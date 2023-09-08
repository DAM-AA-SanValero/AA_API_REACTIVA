package com.svalero.cybershop.controller;

import com.svalero.cybershop.domain.Client;
import com.svalero.cybershop.domain.Discount;
import com.svalero.cybershop.exception.ClientNotFoundException;
import com.svalero.cybershop.exception.DiscountNotFoundException;
import com.svalero.cybershop.exception.ErrorMessage;
import com.svalero.cybershop.service.DiscountService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DiscountController {

    @Autowired
    DiscountService discountService;

    @GetMapping(value = "/discounts", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<Flux<Discount>> getDiscounts(@RequestParam(name="product",required = false,defaultValue = "")
                                                      String product) throws DiscountNotFoundException {
        if(product.equals("")){
            return ResponseEntity.status(200).body(discountService.findAll());
        }
        return ResponseEntity.status(200).body(discountService.filterByProduct(product));
    }

    @GetMapping(value = "/discounts/{id}")
    public ResponseEntity<Mono<Discount>> getDiscount(@PathVariable String id) throws DiscountNotFoundException{
       Mono<Discount> discount = discountService.findById(id);
       return ResponseEntity.status(200).body(discount);
    }

    @PostMapping(value = "/discounts")
    public ResponseEntity<Mono<Discount>> addDiscount(@Valid @RequestBody Discount discount){
        Mono<Discount> newDiscount = discountService.addDiscount(discount);

        return ResponseEntity.status(201).body(newDiscount);
    }

    @DeleteMapping(value= "discounts/{id}")
    public Mono<ResponseEntity<Void>> deleteDiscount(@PathVariable String id) throws DiscountNotFoundException{
        return discountService.deleteDiscount(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .onErrorResume(e -> e instanceof DiscountNotFoundException, e -> Mono.just(ResponseEntity.notFound().build()));
    }


    @ExceptionHandler(DiscountNotFoundException.class)
    public ResponseEntity<ErrorMessage> discountNotFoundException(DiscountNotFoundException dnfe){
        ErrorMessage notfound = new ErrorMessage(404,dnfe.getMessage());
        return new ResponseEntity<>(notfound, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> badRequestException(MethodArgumentNotValidException manve){

        Map<String, String> errors = new HashMap<>();
        manve.getBindingResult().getAllErrors().forEach(error -> {
            String fieldname = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldname, message);
        });

        ErrorMessage badRequest = new ErrorMessage(400, "Bad Request", errors);
        return new ResponseEntity<>(badRequest, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception e) {
        ErrorMessage errorMessage = new ErrorMessage(500, "Internal Server Error");
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
