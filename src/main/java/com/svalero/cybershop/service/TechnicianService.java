package com.svalero.cybershop.service;

import com.svalero.cybershop.domain.Technician;
import com.svalero.cybershop.exception.TechnicianNotFoundException;

import java.util.List;

public interface TechnicianService {

    List<Technician> findAll();

    Technician findById(long id) throws TechnicianNotFoundException;

    Technician addTechnician(Technician technician);

    void deleteTechnician(long id) throws TechnicianNotFoundException;

    Technician updateTechnician(long id, Technician technician) throws TechnicianNotFoundException;

    Technician updateTechnicianAvailability(long id, boolean availability) throws TechnicianNotFoundException;

    List<Technician> filterByNumber(int number) throws TechnicianNotFoundException;

}
