package com.svalero.cybershop.service;

import com.svalero.cybershop.domain.Client;
import com.svalero.cybershop.exception.ClientNotFoundException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ClientService {

    Flux<Client> findAll();

    Flux<Client> filterByVip(boolean vip) throws ClientNotFoundException;
    Mono<Client> findById(String id) throws ClientNotFoundException;

    Mono<Client> addClient(Client client);

    Mono<Void> deleteClient(String id) throws ClientNotFoundException;




}
