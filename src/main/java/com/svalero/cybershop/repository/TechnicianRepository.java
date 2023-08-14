package com.svalero.cybershop.repository;

import com.svalero.cybershop.domain.Technician;
import com.svalero.cybershop.exception.TechnicianNotFoundException;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
public interface TechnicianRepository extends ReactiveMongoRepository<Technician, String> {

    Flux<Technician> findAll();

    Flux<Technician> findByNumber(int number) throws TechnicianNotFoundException;
}
