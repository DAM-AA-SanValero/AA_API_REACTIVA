package com.svalero.cybershop.controller;

import com.svalero.cybershop.domain.Technician;
import com.svalero.cybershop.exception.ErrorMessage;
import com.svalero.cybershop.exception.TechnicianNotFoundException;
import com.svalero.cybershop.service.TechnicianService;
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
public class TechnicianController {
    @Autowired
    TechnicianService technicianService;

    @GetMapping(value = "/technicians", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<Flux<Technician>> getTechnicians(@RequestParam(name="number", required = false, defaultValue = "")
                                                          String number) throws TechnicianNotFoundException{
        if(number.equals("")){
            return ResponseEntity.status(200).body(technicianService.findAll());
        }
        return ResponseEntity.status(200).body(technicianService.filterByNumber(Integer.parseInt(number)));

    }

    @GetMapping("/technicians/{id}")
    public ResponseEntity<Mono<Technician>> getTechnician(@PathVariable String id) throws TechnicianNotFoundException{
        Mono<Technician> technician = technicianService.findById(id);
        return ResponseEntity.status(200).body(technician);
    }

    @PostMapping("technicians")
    public ResponseEntity<Mono<Technician>> addTechnician(@Valid @RequestBody Technician technician){
        Mono<Technician> newTechnician = technicianService.addTechnician(technician);
        return ResponseEntity.status(201).body(newTechnician);
    }

    @DeleteMapping("technicians/{id}")
    public ResponseEntity<Mono<Technician>> deleteTechnician(@PathVariable String id) throws TechnicianNotFoundException{
        technicianService.deleteTechnician(id);
        return ResponseEntity.noContent().build();
    }
    @ExceptionHandler(TechnicianNotFoundException.class)
    public ResponseEntity<ErrorMessage> techinicianNotFoundException(TechnicianNotFoundException tnfe){
        ErrorMessage notfound = new ErrorMessage(404,tnfe.getMessage());
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
