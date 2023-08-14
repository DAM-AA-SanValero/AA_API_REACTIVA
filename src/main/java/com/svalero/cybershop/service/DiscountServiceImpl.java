package com.svalero.cybershop.service;

import com.svalero.cybershop.domain.Discount;
import com.svalero.cybershop.exception.ClientNotFoundException;
import com.svalero.cybershop.exception.DiscountNotFoundException;
import com.svalero.cybershop.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    DiscountRepository discountRepository;

    @Override
    public List<Discount> findAll() {
        return discountRepository.findAll();
    }

    @Override
    public Discount findById(long id) throws DiscountNotFoundException{
        return discountRepository.findById(id).orElseThrow(DiscountNotFoundException::new);
    }

    @Override
    public Discount addDiscount(Discount discount) {
        return discountRepository.save(discount);
    }

    public void deleteDiscount(long id) throws DiscountNotFoundException {
        Discount discount = discountRepository.findById(id).orElseThrow(DiscountNotFoundException::new);
        discountRepository.delete(discount);
    }

    @Override
    public Discount updateDiscount(long id, Discount updateDiscount) throws DiscountNotFoundException{
        Discount oldDiscount = discountRepository.findById(id).orElseThrow(DiscountNotFoundException::new);
        oldDiscount.setProduct(updateDiscount.getProduct());
        oldDiscount.setEvent(updateDiscount.getEvent());
        oldDiscount.setDiscounted(updateDiscount.getDiscounted());
        oldDiscount.setStartDiscount(updateDiscount.getStartDiscount());
        oldDiscount.setEndDiscount(updateDiscount.getEndDiscount());

        return discountRepository.save(oldDiscount);
    }

    @Override
    public Discount updateDiscountDifference(long id, float newDiscount) throws DiscountNotFoundException {
        Discount discount = discountRepository.findById(id).orElseThrow(DiscountNotFoundException::new);
        discount.setDiscounted(newDiscount);
        return discountRepository.save(discount);
    }

    @Override
    public List<Discount> filterByProduct(String product) throws DiscountNotFoundException {
        return discountRepository.findByProduct(product);
    }
}
