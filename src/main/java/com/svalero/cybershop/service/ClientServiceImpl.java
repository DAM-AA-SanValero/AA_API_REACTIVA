package com.svalero.cybershop.service;

import com.svalero.cybershop.domain.Client;
import com.svalero.cybershop.exception.ClientNotFoundException;
import com.svalero.cybershop.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService{

    @Autowired
    ClientRepository clientRepository;

    @Override
    public Flux<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Flux<Client> filterByVip(boolean vip) throws ClientNotFoundException {
        return clientRepository.findByVip(vip);
    }
    @Override
    public Mono<Client> findById(String id) throws ClientNotFoundException{
        return clientRepository.findById(id).onErrorReturn(new Client());
    }

    @Override
    public Mono<Client> addClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Mono<Client> deleteClient(String id) throws ClientNotFoundException {
        Mono<Client> client = clientRepository.findById(id).onErrorReturn(new Client());
        clientRepository.delete(client.block());
        return client;
    }
}
