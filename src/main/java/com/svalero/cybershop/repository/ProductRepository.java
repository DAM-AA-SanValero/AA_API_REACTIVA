package com.svalero.cybershop.repository;

import com.svalero.cybershop.domain.Product;
import com.svalero.cybershop.exception.ProductNotFoundException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findAll();
    List<Product> findByInStock(boolean stock) throws ProductNotFoundException;

}
