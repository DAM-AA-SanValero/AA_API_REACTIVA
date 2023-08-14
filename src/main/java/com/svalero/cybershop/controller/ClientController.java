package com.svalero.cybershop.controller;

import com.svalero.cybershop.domain.Client;
import com.svalero.cybershop.exception.ClientNotFoundException;
import com.svalero.cybershop.exception.ErrorMessage;
import com.svalero.cybershop.service.ClientService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ClientController {

    @Autowired
    ClientService clientService;

     @GetMapping(value = "/clients", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
        public ResponseEntity<Flux<Client>> getClients(@RequestParam(name = "vip", required = false, defaultValue = "")
                                                            String vip) throws ClientNotFoundException {

        if(vip.equals("")){
            return ResponseEntity.status(200).body(clientService.findAll());
        }
        return ResponseEntity.status(200).body(clientService.filterByVip(Boolean.parseBoolean(vip)));
    }

    @GetMapping(value = "/clients/{id}")
    public ResponseEntity<Mono<Client>> getClient(@PathVariable String id) throws ClientNotFoundException{
        Mono<Client> client = clientService.findById(id);
        return ResponseEntity.status(200).body(client);
    }

    @PostMapping(value = "/clients")
    public ResponseEntity<Mono<Client>> addClient(@Valid @RequestBody Client client){
        Mono<Client> newClient = clientService.addClient(client);
        return ResponseEntity.status(201).body(newClient);
    }

    @DeleteMapping("/clients/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable String id) throws ClientNotFoundException{
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ErrorMessage> clientNotFoundException(ClientNotFoundException cnfe){
        ErrorMessage notfound = new ErrorMessage(404,cnfe.getMessage());
        return new ResponseEntity<>(notfound, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> badRequestException(MethodArgumentNotValidException manve){

        //Indicar en que campo ha fallado el cliente
        Map<String, String> errors = new HashMap<>();
        manve.getBindingResult().getAllErrors().forEach(error -> {
            String fieldname = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldname, message);
        });

        ErrorMessage badRequest = new ErrorMessage(400, "Bad Request", errors);
        return new ResponseEntity<>(badRequest, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception e) {
        ErrorMessage errorMessage = new ErrorMessage(500, "Internal Server Error");
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
