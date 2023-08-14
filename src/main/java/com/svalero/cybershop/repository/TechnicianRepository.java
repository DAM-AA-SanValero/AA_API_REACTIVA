package com.svalero.cybershop.repository;

import com.svalero.cybershop.domain.Technician;
import com.svalero.cybershop.exception.TechnicianNotFoundException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechnicianRepository extends CrudRepository<Technician, Long> {

    List<Technician> findAll();

    List<Technician> findByNumber(int number) throws TechnicianNotFoundException;
}
