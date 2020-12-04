package andreasgroup.medicineinventoryservice.web.controllers;

import andreasgroup.medicineinventoryservice.repositories.MedicineInventoryRepository;
import andreasgroup.medicineinventoryservice.web.mappers.MedicineInventoryMapper;
import andreasgroup.production.model.MedicineInventoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created on 26/Nov/2020 to microservices-medicine-production
 */
@RequiredArgsConstructor
@Slf4j
@RestController
public class MedicineInventoryController {

    private final MedicineInventoryRepository  medicineInventoryRepository;
    private final MedicineInventoryMapper medicineInventoryMapper;

    @GetMapping("api/v1/medicine/{medicineId}/inventory")
    List<MedicineInventoryDto> listMedicinesById(@PathVariable UUID medicineId){
        log.debug("Finding Inventory for medicine with ID: " + medicineId);

        return medicineInventoryRepository
                .findAllByMedicineId(medicineId)
                .stream()
                .map(medicineInventoryMapper::medicineInventoryToMedicineInventoryDto)
                .collect(Collectors.toList());
    }
}
