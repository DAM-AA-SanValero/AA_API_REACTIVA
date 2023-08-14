package com.svalero.cybershop.service;

import com.svalero.cybershop.domain.Discount;
import com.svalero.cybershop.exception.ClientNotFoundException;
import com.svalero.cybershop.exception.DiscountNotFoundException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface DiscountService {

    Flux<Discount> findAll();

    Flux<Discount> filterByProduct(String product) throws DiscountNotFoundException;

    Mono<Discount> findById(String id) throws DiscountNotFoundException;

    Mono<Discount> addDiscount(Discount discount);

    Mono<Discount> deleteDiscount(String id) throws DiscountNotFoundException;


}
