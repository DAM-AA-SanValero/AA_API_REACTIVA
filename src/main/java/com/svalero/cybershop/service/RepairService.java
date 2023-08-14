package com.svalero.cybershop.service;

import com.svalero.cybershop.domain.Repair;
import com.svalero.cybershop.exception.RepairNotFoundException;
import com.svalero.cybershop.repository.RepairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

public interface RepairService {

  Flux<Repair> findAll();

  Mono<Repair> findById(String id) throws RepairNotFoundException;

  Mono<Repair> addRepair(Repair repair);

  Mono<Repair> deleteRepair(String id) throws RepairNotFoundException;

  Flux<Repair> filterByShipmentDate(LocalDate shipmentDate) throws RepairNotFoundException;

}
