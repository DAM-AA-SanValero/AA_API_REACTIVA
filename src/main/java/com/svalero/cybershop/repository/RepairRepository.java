package com.svalero.cybershop.repository;

import com.svalero.cybershop.domain.Repair;
import com.svalero.cybershop.exception.ProductNotFoundException;
import com.svalero.cybershop.exception.RepairNotFoundException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RepairRepository extends CrudRepository<Repair, Long> {

    List<Repair> findAll();

    List<Repair> findByShipmentDate(LocalDate shipmentDate) throws RepairNotFoundException;

}
