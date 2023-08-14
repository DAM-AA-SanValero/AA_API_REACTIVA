package com.svalero.cybershop.service;

import com.svalero.cybershop.domain.Technician;
import com.svalero.cybershop.exception.TechnicianNotFoundException;
import com.svalero.cybershop.repository.TechnicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnicianServiceImpl implements TechnicianService{

    @Autowired
    TechnicianRepository technicianRepository;

    @Override
    public List<Technician> findAll() {
        return technicianRepository.findAll();
    }

    @Override
    public Technician findById(long id) throws TechnicianNotFoundException {
        return technicianRepository.findById(id).orElseThrow(TechnicianNotFoundException::new);
    }
    @Override
    public Technician addTechnician(Technician technician) {
        return technicianRepository.save(technician);
    }

    @Override
    public void deleteTechnician(long id) throws TechnicianNotFoundException{
        Technician technician = technicianRepository.findById(id).orElseThrow(TechnicianNotFoundException::new);
        technicianRepository.delete(technician);
    }

    @Override
    public Technician updateTechnician(long id, Technician updateTechnician) throws TechnicianNotFoundException {
        Technician oldTechnician = technicianRepository.findById(id).orElseThrow(TechnicianNotFoundException::new);
        oldTechnician.setName(updateTechnician.getName());
        oldTechnician.setSurname(updateTechnician.getSurname());
        oldTechnician.setNumber(updateTechnician.getNumber());
        oldTechnician.setDepartment(updateTechnician.getDepartment());
        oldTechnician.setAvailable(updateTechnician.isAvailable());

        return technicianRepository.save(oldTechnician);
    }

    @Override
    public Technician updateTechnicianAvailability(long id, boolean availability) throws TechnicianNotFoundException {
        Technician technician = technicianRepository.findById(id).orElseThrow(TechnicianNotFoundException::new);
        technician.setAvailable(availability);
        return technicianRepository.save(technician);
    }

    @Override
    public List<Technician> filterByNumber(int number) throws TechnicianNotFoundException {
        return technicianRepository.findByNumber(number);
    }


}
