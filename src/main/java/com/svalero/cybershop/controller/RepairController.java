package com.svalero.cybershop.controller;

import com.svalero.cybershop.domain.Repair;
import com.svalero.cybershop.exception.ErrorMessage;
import com.svalero.cybershop.exception.ProductNotFoundException;
import com.svalero.cybershop.exception.RepairNotFoundException;
import com.svalero.cybershop.service.RepairService;
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


import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RepairController {

    @Autowired
    RepairService repairService;

    @GetMapping(value = "/repairs", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<Flux<Repair>> getRepairs(@RequestParam(name="shipmentDate",required = false, defaultValue = "")
                                                  String shipmentDate) throws RepairNotFoundException {
        if(shipmentDate.equals("")){
            return ResponseEntity.status(200).body(repairService.findAll());
        }
        return ResponseEntity.status(200).body(repairService.filterByShipmentDate(LocalDate.parse(shipmentDate)));

    }

    @GetMapping(value = "/repairs/{id}")
    public ResponseEntity<Mono<Repair>> getRepair(@PathVariable String id) throws RepairNotFoundException{
        Mono<Repair> repair = repairService.findById(id);
        return ResponseEntity.status(200).body(repair);
    }

    @PostMapping(value = "/repairs")
    public ResponseEntity<Mono<Repair>> addRepair(@Valid @RequestBody Repair repair){
        Mono<Repair> newRepair = repairService.addRepair(repair);
        return ResponseEntity.status(201).body(newRepair);
    }

    @DeleteMapping(value = "/repairs/{id}")
    public Mono<ResponseEntity<Void>> deleteRepair(@PathVariable String id) throws RepairNotFoundException {
        return repairService.deleteRepair(id)
                .then(Mono.just((ResponseEntity.noContent().<Void>build())))
                .onErrorResume(e -> e instanceof RepairNotFoundException, e -> Mono.just(ResponseEntity.notFound().build()));
    }

    @ExceptionHandler(RepairNotFoundException.class)
    public ResponseEntity<ErrorMessage> repairNotFoundException(RepairNotFoundException rnfe){
        ErrorMessage notfound = new ErrorMessage(404,rnfe.getMessage());
        return new ResponseEntity<>(notfound, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> badRequestException(MethodArgumentNotValidException manve){

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
