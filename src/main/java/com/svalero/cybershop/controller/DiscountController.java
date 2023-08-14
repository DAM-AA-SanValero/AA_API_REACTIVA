package com.svalero.cybershop.controller;

import com.svalero.cybershop.domain.Client;
import com.svalero.cybershop.domain.Discount;
import com.svalero.cybershop.exception.ClientNotFoundException;
import com.svalero.cybershop.exception.DiscountNotFoundException;
import com.svalero.cybershop.exception.ErrorMessage;
import com.svalero.cybershop.service.DiscountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DiscountController {

    private final Logger logger = LoggerFactory.getLogger(DiscountController.class);
    @Autowired
    DiscountService discountService;

    @GetMapping("/discounts")
    public ResponseEntity<List<Discount>> getDiscount(@RequestParam(name="product",required = false,defaultValue = "")
                                                      String product) throws DiscountNotFoundException {
        if(product.equals("")){
            logger.info("Lista de descuentos mostrada");
            return ResponseEntity.status(200).body(discountService.findAll());
        }
        logger.info("Filtro por nombre del producto descontado");
        return ResponseEntity.status(200).body(discountService.filterByProduct(product));
    }

    @GetMapping("/discounts/{id}")
    public ResponseEntity<Discount> getDiscount(@PathVariable long id) throws DiscountNotFoundException{
       Discount discount = discountService.findById(id);
        logger.info("Descuento mostrado con el id: "+id);
       return ResponseEntity.status(200).body(discount);
    }

    @PostMapping("/discounts")
    public ResponseEntity<Discount> addDiscount(@Valid @RequestBody Discount discount){
        Discount newDiscount = discountService.addDiscount(discount);
        logger.info("Descuento añadido");
        return ResponseEntity.status(201).body(newDiscount);
    }

    @DeleteMapping("discounts/{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable long id) throws DiscountNotFoundException{
        discountService.deleteDiscount(id);
        logger.info("Descuento borrado");
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/discounts/{id}")
    public ResponseEntity<Discount> updateDiscount(@PathVariable long id, @RequestBody Discount discount) throws DiscountNotFoundException{
        Discount updateDiscount = discountService.updateDiscount(id, discount);
        logger.info("Descuento actualizado");
        return ResponseEntity.status(200).body(updateDiscount);
    }

    @PatchMapping("/discounts/{id}")
    public ResponseEntity<Discount> updateDiscountPartially(@PathVariable long id, @RequestBody Discount discount) throws DiscountNotFoundException{
        Discount updateDiscountDifference = discountService.updateDiscountDifference(id, discount.getDiscounted());
        logger.info("Precio de descuento actualizado a: "+ discount.getDiscounted());
        return ResponseEntity.status(200).body(updateDiscountDifference);
    }

    @ExceptionHandler(DiscountNotFoundException.class)
    public ResponseEntity<ErrorMessage> discountNotFoundException(DiscountNotFoundException dnfe){
        logger.error(dnfe.getMessage(),dnfe);
        ErrorMessage notfound = new ErrorMessage(404,dnfe.getMessage());
        return new ResponseEntity<>(notfound, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> badRequestException(MethodArgumentNotValidException manve){

        //Indicar en que campo ha fallado el cliente
        Map<String, String> errors = new HashMap<>();
        manve.getBindingResult().getAllErrors().forEach(error -> {
            String fieldname = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldname, message);
        });

        logger.error(manve.getMessage(),manve);
        ErrorMessage badRequest = new ErrorMessage(400, "Bad Request", errors);
        return new ResponseEntity<>(badRequest, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception e) {
        logger.error(e.getMessage(),e);
        ErrorMessage errorMessage = new ErrorMessage(500, "Internal Server Error");
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
