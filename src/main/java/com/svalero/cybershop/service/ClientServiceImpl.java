package com.svalero.cybershop.service;

import com.svalero.cybershop.domain.Client;
import com.svalero.cybershop.exception.ClientNotFoundException;
import com.svalero.cybershop.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService{

    @Autowired
    ClientRepository clientRepository;

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client findById(long id) throws ClientNotFoundException{
        return clientRepository.findById(id).orElseThrow(ClientNotFoundException::new);
    }

    @Override
    public Client addClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public void deleteClient(long id) throws ClientNotFoundException {
        Client client = clientRepository.findById(id).orElseThrow(ClientNotFoundException::new);
        clientRepository.delete(client);
    }

    @Override
    public Client updateClient(long id, Client updateClient) throws ClientNotFoundException {
        Client oldClient = clientRepository.findById(id).orElseThrow(ClientNotFoundException::new);
        oldClient.setName(updateClient.getName());
        oldClient.setSurname(updateClient.getSurname());
        oldClient.setNumber(updateClient.getNumber());
        oldClient.setRegisterDate(updateClient.getRegisterDate());
        oldClient.setVip(updateClient.isVip());
        return clientRepository.save(oldClient);
    }

    @Override
    public Client updateClientName(long id, String newName) throws ClientNotFoundException {
        Client client = clientRepository.findById(id).orElseThrow(ClientNotFoundException::new);
        client.setName(newName);
        return clientRepository.save(client);
    }

    @Override
    public List<Client> filterByVip(boolean vip) throws ClientNotFoundException {
        return clientRepository.findByVip(vip);
    }


}
