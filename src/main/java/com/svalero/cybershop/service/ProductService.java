package com.svalero.cybershop.service;

import com.svalero.cybershop.domain.Client;
import com.svalero.cybershop.domain.Discount;
import com.svalero.cybershop.domain.Product;
import com.svalero.cybershop.exception.ClientNotFoundException;
import com.svalero.cybershop.exception.ProductNotFoundException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ProductService {
    Flux<Product> findAll();

    Flux<Product> filterByInStock(boolean stock) throws ProductNotFoundException;
    Mono<Product> findById(String id) throws ProductNotFoundException;

    Mono<Product> addProduct(Product product);

    Mono<Void> deleteProduct(String id) throws ProductNotFoundException;


}

