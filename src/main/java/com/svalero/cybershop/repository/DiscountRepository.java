package com.svalero.cybershop.repository;

import com.svalero.cybershop.domain.Discount;
import com.svalero.cybershop.exception.DiscountNotFoundException;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
public interface DiscountRepository extends ReactiveMongoRepository<Discount, String> {

    Flux<Discount> findAll();
    Flux<Discount> findByProduct(String product) throws DiscountNotFoundException;



}
