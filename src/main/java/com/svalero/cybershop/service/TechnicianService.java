package com.svalero.cybershop.service;

import com.svalero.cybershop.domain.Technician;
import com.svalero.cybershop.exception.TechnicianNotFoundException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface TechnicianService {

    Flux<Technician> findAll();

    Flux<Technician> filterByNumber(int number) throws TechnicianNotFoundException;
    Mono<Technician> findById(String id) throws TechnicianNotFoundException;

    Mono<Technician> addTechnician(Technician technician);

    Mono<Technician> deleteTechnician(String id) throws TechnicianNotFoundException;



}
