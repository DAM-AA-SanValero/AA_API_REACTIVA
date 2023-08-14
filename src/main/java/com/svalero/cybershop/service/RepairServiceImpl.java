package com.svalero.cybershop.service;

import com.svalero.cybershop.domain.Repair;
import com.svalero.cybershop.exception.RepairNotFoundException;
import com.svalero.cybershop.repository.RepairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RepairServiceImpl implements RepairService {

    @Autowired
    RepairRepository repairRepository;


    @Override
    public List<Repair> findAll() {
        return repairRepository.findAll();
    }

    @Override
    public Repair findById(long id) throws RepairNotFoundException {
        return repairRepository.findById(id).orElseThrow(RepairNotFoundException::new);
    }

    @Override
    public Repair addRepair(Repair repair) {
        return repairRepository.save(repair);
    }

    @Override
    public void deleteRepair(long id) throws RepairNotFoundException {
        Repair repair = repairRepository.findById(id).orElseThrow(RepairNotFoundException::new);
        repairRepository.delete(repair);
    }

    @Override
    public Repair updateRepair(long id, Repair updateRepair) throws RepairNotFoundException{
        Repair oldRepair = repairRepository.findById(id).orElseThrow(RepairNotFoundException::new);
        oldRepair.setComponent(updateRepair.getComponent());
        oldRepair.setPrice(updateRepair.getPrice());
        oldRepair.setShippingAddress(updateRepair.getShippingAddress());
        oldRepair.setShipmentDate(updateRepair.getShipmentDate());
        oldRepair.setRepairedDate(updateRepair.getRepairedDate());

        return repairRepository.save(oldRepair);
    }

    @Override
    public Repair updateRepairedDate(long id, LocalDate newRepair) throws RepairNotFoundException {
        Repair repair = repairRepository.findById(id).orElseThrow(RepairNotFoundException::new);
        repair.setRepairedDate(newRepair);
        return repairRepository.save(repair);
    }

    @Override
    public List<Repair> filterByShipmentDate(LocalDate shipmentDate) throws RepairNotFoundException {
        return repairRepository.findByShipmentDate(shipmentDate);
    }
}
