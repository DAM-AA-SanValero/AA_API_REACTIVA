package com.svalero.cybershop.controller;

import com.svalero.cybershop.domain.Technician;
import com.svalero.cybershop.exception.ErrorMessage;
import com.svalero.cybershop.exception.TechnicianNotFoundException;
import com.svalero.cybershop.service.TechnicianService;
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
public class TechnicianController {

    private final Logger logger = LoggerFactory.getLogger(TechnicianController.class);
    @Autowired
    TechnicianService technicianService;

    @GetMapping("/technicians")
    public ResponseEntity<List<Technician>> getTechnician(@RequestParam(name="number", required = false, defaultValue = "")
                                                          String number) throws TechnicianNotFoundException{
        if(number.equals("")){
            logger.info("Lista de técnicos mostrada");
            return ResponseEntity.status(200).body(technicianService.findAll());
        }
        logger.info("Lista filtrada por numero de técnico");
        return ResponseEntity.status(200).body(technicianService.filterByNumber(Integer.parseInt(number)));

    }

    @GetMapping("/technicians/{id}")
    public ResponseEntity<Technician> getTechnician(@PathVariable long id) throws TechnicianNotFoundException{
        Technician technician = technicianService.findById(id);
        logger.info("Técnico mostrado con el id: "+id);
        return ResponseEntity.status(200).body(technician);
    }

    @PostMapping("technicians")
    public ResponseEntity<Technician> addTechnician(@Valid @RequestBody Technician technician){
        Technician newTechnician = technicianService.addTechnician(technician);
        logger.info("Técnico añadido");
        return ResponseEntity.status(201).body(newTechnician);
    }

    @DeleteMapping("technicians/{id}")
    public ResponseEntity<Void> deleteTechnician(@PathVariable long id) throws TechnicianNotFoundException{
        technicianService.deleteTechnician(id);
        logger.info("Técnico borrado");
        return ResponseEntity.noContent().build();
    }
    @PutMapping("technicians/{id}")
    public ResponseEntity<Technician> updateTechnician(@PathVariable long id, @RequestBody Technician technician)
            throws TechnicianNotFoundException{
        Technician updateTechnician = technicianService.updateTechnician(id,technician);
        logger.info("Técnico actualizado");
        return ResponseEntity.status(200).body(updateTechnician);

    }

    @PatchMapping("technicians/{id}")
    public ResponseEntity<Technician> updateTechnicianPartially(@PathVariable long id, @RequestBody Technician technician)
            throws TechnicianNotFoundException{
        Technician updateTechnicianAvailability = technicianService.updateTechnicianAvailability(id,technician.isAvailable());
        logger.info("¿Disponibilidad del técnico?: "+technician.isAvailable());
        return ResponseEntity.status(200).body(updateTechnicianAvailability);

    }

    @ExceptionHandler(TechnicianNotFoundException.class)
    public ResponseEntity<ErrorMessage> techinicianNotFoundException(TechnicianNotFoundException tnfe){
        logger.error(tnfe.getMessage(),tnfe);
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
