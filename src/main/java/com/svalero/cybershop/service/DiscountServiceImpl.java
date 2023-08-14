package com.svalero.cybershop.service;

import com.svalero.cybershop.domain.Discount;
import com.svalero.cybershop.exception.ClientNotFoundException;
import com.svalero.cybershop.exception.DiscountNotFoundException;
import com.svalero.cybershop.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    DiscountRepository discountRepository;

    @Override
    public Flux<Discount> findAll() {
        return discountRepository.findAll();
    }

    @Override
    public Flux<Discount> filterByProduct(String product) throws DiscountNotFoundException {
        return discountRepository.findByProduct(product);
    }
    @Override
    public Mono<Discount> findById(String id) throws DiscountNotFoundException{
        return discountRepository.findById(id).onErrorReturn(new Discount());
    }

    @Override
    public Mono<Discount> addDiscount(Discount discount) {
        return discountRepository.save(discount);
    }

    public Mono<Discount> deleteDiscount(String id) throws DiscountNotFoundException {
        Mono<Discount> discount = discountRepository.findById(id).onErrorReturn(new Discount());
        discountRepository.delete(discount.block());
        return discount;
    }
}
