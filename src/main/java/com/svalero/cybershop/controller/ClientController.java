package com.svalero.cybershop.controller;

import com.svalero.cybershop.domain.Client;
import com.svalero.cybershop.exception.ClientNotFoundException;
import com.svalero.cybershop.exception.ErrorMessage;
import com.svalero.cybershop.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ClientController {

    private final Logger logger = LoggerFactory.getLogger(ClientController.class);
    @Autowired
    ClientService clientService;

     @GetMapping("/clients")
        public ResponseEntity<List<Client>> getClients(@RequestParam(name = "vip", required = false, defaultValue = "")
                                                            String vip) throws ClientNotFoundException {

        if(vip.equals("")){
            logger.info("Lista de clientes mostrada");
            return ResponseEntity.status(200).body(clientService.findAll());
        }
         logger.info("Filtro por cliente VIP");
        return ResponseEntity.status(200).body(clientService.filterByVip(Boolean.parseBoolean(vip)));
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<Client> getClient(@PathVariable long id) throws ClientNotFoundException{
        Client client = clientService.findById(id);
        logger.info("Cliente mostrado con el id: "+id);
        return ResponseEntity.status(200).body(client);
    }

    @PostMapping("/clients")
    public ResponseEntity<Client> addClient(@Valid @RequestBody Client client){
        Client newClient = clientService.addClient(client);
        logger.info("Cliente añadido");
        return ResponseEntity.status(201).body(newClient);
    }

    @DeleteMapping("/clients/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable long id) throws ClientNotFoundException{
        clientService.deleteClient(id);
        logger.info("Cliente borrado");
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/clients/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable long id,@Valid @RequestBody Client client) throws ClientNotFoundException {
        Client updateClient =clientService.updateClient(id, client);
        logger.info("Cliente modificado");
        return ResponseEntity.status(200).body(updateClient);
    }

    @PatchMapping("/clients/{id}")
    public ResponseEntity<Client> updateClientPartially(@PathVariable long id,@Valid @RequestBody Client client) throws ClientNotFoundException{
        Client updateClientName = clientService.updateClientName(id, client.getName());
        logger.info("Nombre del cliente actualizado a: "+ client.getName());
        return ResponseEntity.status(200).body(updateClientName);
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ErrorMessage> clientNotFoundException(ClientNotFoundException cnfe){
        logger.error(cnfe.getMessage(),cnfe);
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

        logger.error(manve.getMessage(),manve);
        ErrorMessage badRequest = new ErrorMessage(400, "Bad Request", errors);
        return new ResponseEntity<>(badRequest, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception e) {
        logger.error(e.getMessage(),e);
        ErrorMessage errorMessage = new ErrorMessage(500, "Internal Server Error");
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
