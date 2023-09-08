package com.svalero.cybershop.repository;

import com.svalero.cybershop.domain.Product;
import com.svalero.cybershop.exception.ProductNotFoundException;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
    Flux<Product> findAll();
    Flux<Product> findByInStock(boolean stock) throws ProductNotFoundException;

}
