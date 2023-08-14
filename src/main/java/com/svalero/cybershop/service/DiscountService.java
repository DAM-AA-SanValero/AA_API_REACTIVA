package com.svalero.cybershop.service;

import com.svalero.cybershop.domain.Discount;
import com.svalero.cybershop.exception.ClientNotFoundException;
import com.svalero.cybershop.exception.DiscountNotFoundException;

import java.util.List;

public interface DiscountService {

    List<Discount> findAll();

    Discount findById(long id) throws DiscountNotFoundException;

    Discount addDiscount(Discount discount);

    void deleteDiscount(long id) throws DiscountNotFoundException;
    Discount updateDiscount(long id, Discount discount) throws DiscountNotFoundException;

    Discount updateDiscountDifference(long id, float newDiscount) throws DiscountNotFoundException;

    List<Discount> filterByProduct(String product) throws DiscountNotFoundException;
}
