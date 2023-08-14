package com.svalero.cybershop.service;

import com.svalero.cybershop.domain.Repair;
import com.svalero.cybershop.exception.RepairNotFoundException;
import com.svalero.cybershop.repository.RepairRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

public interface RepairService {

  List<Repair> findAll();

  Repair findById(long id) throws RepairNotFoundException;

  Repair addRepair(Repair repair);

  void deleteRepair(long id) throws RepairNotFoundException;

  Repair updateRepair(long id, Repair repair) throws RepairNotFoundException;

  Repair updateRepairedDate(long id, LocalDate newRepair) throws  RepairNotFoundException;

  List<Repair> filterByShipmentDate(LocalDate shipmentDate) throws RepairNotFoundException;

}
