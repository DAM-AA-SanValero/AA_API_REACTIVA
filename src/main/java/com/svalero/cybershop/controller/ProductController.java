package com.svalero.cybershop.controller;

import com.svalero.cybershop.domain.Product;
import com.svalero.cybershop.exception.ErrorMessage;
import com.svalero.cybershop.exception.ProductNotFoundException;
import com.svalero.cybershop.service.ProductService;
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
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping(value = "/products", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<Flux<Product>> getProducts(@RequestParam(name="stock",required = false, defaultValue = "")
                                                        String stock) throws ProductNotFoundException{
        if(stock.equals("")){
            return ResponseEntity.status(200).body(productService.findAll());
        }
        return ResponseEntity.status(200).body(productService.filterByInStock(Boolean.parseBoolean(stock)));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Mono<Product>> getProduct(@PathVariable String id) throws ProductNotFoundException{
        Mono<Product> product = productService.findById(id);
        return ResponseEntity.status(200).body(product);
    }

    @PostMapping("/products")
    public ResponseEntity<Mono<Product>> addProduct(@Valid @RequestBody Product product){
        Mono<Product> newProduct = productService.addProduct(product);
        return ResponseEntity.status(201).body(newProduct);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Mono<Product>> deleteProduct(@PathVariable String id) throws ProductNotFoundException{
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorMessage> productNotFoundException(ProductNotFoundException pnfe){
        ErrorMessage notfound = new ErrorMessage(404,pnfe.getMessage());
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

        ErrorMessage badRequest = new ErrorMessage(400, "Bad Request", errors);
        return new ResponseEntity<>(badRequest, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception e) {
        ErrorMessage errorMessage = new ErrorMessage(500, "Internal Server Error");
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
