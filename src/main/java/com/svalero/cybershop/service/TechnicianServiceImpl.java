package com.svalero.cybershop.service;

import com.svalero.cybershop.domain.Discount;
import com.svalero.cybershop.domain.Repair;
import com.svalero.cybershop.domain.Technician;
import com.svalero.cybershop.exception.DiscountNotFoundException;
import com.svalero.cybershop.exception.TechnicianNotFoundException;
import com.svalero.cybershop.repository.TechnicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class TechnicianServiceImpl implements TechnicianService{

    @Autowired
    TechnicianRepository technicianRepository;

    @Override
    public Flux<Technician> findAll() {
        return technicianRepository.findAll();
    }

    @Override
    public Flux<Technician> filterByNumber(int number) throws TechnicianNotFoundException {
        return technicianRepository.findByNumber(number);
    }


    @Override
    public Mono<Technician> findById(String id) throws TechnicianNotFoundException {
        return technicianRepository.findById(id).onErrorReturn(new Technician());
    }
    @Override
    public Mono<Technician> addTechnician(Technician technician) {
        return technicianRepository.save(technician);
    }
    @Override
    public Mono<Void> deleteTechnician(String id) throws TechnicianNotFoundException{
        return technicianRepository.findById(id)
                .switchIfEmpty(Mono.error(new TechnicianNotFoundException()))
                .flatMap(existingDiscount -> technicianRepository.delete(existingDiscount)).then(Mono.empty());
    }



}
