package com.svalero.cybershop.service;

import com.svalero.cybershop.domain.Client;
import com.svalero.cybershop.exception.ClientNotFoundException;

import java.util.List;

public interface ClientService {

    List<Client> findAll();
    Client findById(long id) throws ClientNotFoundException;

    Client addClient(Client client);

    void deleteClient(long id) throws ClientNotFoundException;

    Client updateClient(long id, Client updateClient) throws ClientNotFoundException;
    Client updateClientName(long id, String newName) throws ClientNotFoundException;

    List<Client> filterByVip(boolean vip) throws ClientNotFoundException;



}
