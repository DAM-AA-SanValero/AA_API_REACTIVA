package com.svalero.cybershop.service;

import com.svalero.cybershop.domain.Discount;
import com.svalero.cybershop.domain.Repair;
import com.svalero.cybershop.exception.DiscountNotFoundException;
import com.svalero.cybershop.exception.RepairNotFoundException;
import com.svalero.cybershop.repository.RepairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

@Service
public class RepairServiceImpl implements RepairService {

    @Autowired
    RepairRepository repairRepository;


    @Override
    public Flux<Repair> findAll() {
        return repairRepository.findAll();
    }

    @Override
    public Flux<Repair> filterByShipmentDate(LocalDate shipmentDate) throws RepairNotFoundException {
        return repairRepository.findByShipmentDate(shipmentDate);
    }

    @Override
    public Mono<Repair> findById(String id) throws RepairNotFoundException {
        return repairRepository.findById(id).onErrorReturn(new Repair());
    }

    @Override
    public Mono<Repair> addRepair(Repair repair) {
        return repairRepository.save(repair);
    }

    @Override
    public Mono<Void> deleteRepair(String id) throws RepairNotFoundException {
        return repairRepository.findById(id)
                .switchIfEmpty(Mono.error(new RepairNotFoundException()))
                .flatMap(existingDiscount -> repairRepository.delete(existingDiscount)).then(Mono.empty());
    }


}
