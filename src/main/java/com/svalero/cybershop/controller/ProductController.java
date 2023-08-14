package com.svalero.cybershop.controller;

import com.svalero.cybershop.domain.Product;
import com.svalero.cybershop.exception.ErrorMessage;
import com.svalero.cybershop.exception.ProductNotFoundException;
import com.svalero.cybershop.service.ProductService;
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
public class ProductController {

    private final Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProduct(@RequestParam(name="stock",required = false, defaultValue = "")
                                                        String stock) throws ProductNotFoundException{
        if(stock.equals("")){
            logger.info("Lista de productos mostrada");
            return ResponseEntity.status(200).body(productService.findAll());
        }
        logger.info("Filtro por producto en Stock");
        return ResponseEntity.status(200).body(productService.filterByInStock(Boolean.parseBoolean(stock)));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable long id) throws ProductNotFoundException{
        Product product = productService.findById(id);
        logger.info("Producto mostrado con el id: "+id);
        return ResponseEntity.status(200).body(product);
    }

    @PostMapping("/products")
    public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product){
        Product newProduct = productService.addProduct(product);
        logger.info("Producto añadido");
        return ResponseEntity.status(201).body(newProduct);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id) throws ProductNotFoundException{
        productService.deleteProduct(id);
        logger.info("Producto borrado");
        return ResponseEntity.noContent().build();
    }

    @PutMapping("products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable long id, @RequestBody Product product)
            throws ProductNotFoundException{
        Product updateProduct = productService.updateProduct(id, product);
        logger.info("Producto actualizado");
        return ResponseEntity.status(200).body(updateProduct);
    }

    @PatchMapping("products/{id}")
    public ResponseEntity<Product> updateProductPartially(@PathVariable long id, @RequestBody Product product)
            throws ProductNotFoundException{
        Product updateProductPrice = productService.updateProductPrice(id, product.getPrice());
        logger.info("Precio del producto actualizado a "+product.getPrice());
        return ResponseEntity.status(200).body(updateProductPrice);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorMessage> productNotFoundException(ProductNotFoundException pnfe){
        logger.error(pnfe.getMessage(),pnfe);
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
