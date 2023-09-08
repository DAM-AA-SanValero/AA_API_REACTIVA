package com.svalero.cybershop.service;

import com.svalero.cybershop.domain.Client;
import com.svalero.cybershop.domain.Discount;
import com.svalero.cybershop.domain.Product;
import com.svalero.cybershop.exception.ClientNotFoundException;
import com.svalero.cybershop.exception.DiscountNotFoundException;
import com.svalero.cybershop.exception.ProductNotFoundException;
import com.svalero.cybershop.repository.ClientRepository;
import com.svalero.cybershop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductRepository productRepository;

    @Override
    public Flux<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Flux<Product> filterByInStock(boolean stock) throws ProductNotFoundException {
        return productRepository.findByInStock(stock);
    }


    @Override
    public Mono<Product> findById(String id) throws ProductNotFoundException {
        return productRepository.findById(id).onErrorReturn(new Product());
    }
    @Override
    public Mono<Product> addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Mono<Void> deleteProduct(String id) throws ProductNotFoundException {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new ProductNotFoundException()))
                .flatMap(existingDiscount -> productRepository.delete(existingDiscount)).then(Mono.empty());
    }
}
