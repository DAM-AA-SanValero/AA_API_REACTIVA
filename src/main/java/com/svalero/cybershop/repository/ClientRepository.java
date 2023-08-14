package com.svalero.cybershop.repository;

import com.svalero.cybershop.domain.Client;
import com.svalero.cybershop.exception.ClientNotFoundException;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
public interface ClientRepository extends ReactiveMongoRepository<Client, String> {
    Flux<Client> findAll();

    Flux<Client> findByVip(boolean vip) throws ClientNotFoundException;

}
