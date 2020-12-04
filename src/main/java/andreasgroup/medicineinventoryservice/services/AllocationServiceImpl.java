package andreasgroup.medicineinventoryservice.services;

import andreasgroup.medicineinventoryservice.domain.MedicineInventory;
import andreasgroup.medicineinventoryservice.repositories.MedicineInventoryRepository;
import andreasgroup.production.model.MedicineOrderDto;
import andreasgroup.production.model.MedicineOrderLineDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created on 26/Nov/2020 to microservices-medicine-production
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class AllocationServiceImpl implements AllocationService {

    private final MedicineInventoryRepository medicineInventoryRepository;

    @Override
    public Boolean allocateOrder(MedicineOrderDto medicineOrderDto) {

        log.debug("Allocating OrderId:" + medicineOrderDto.getId());
        AtomicInteger totalOrdered = new AtomicInteger();
        AtomicInteger totalAllocated = new AtomicInteger();

        medicineOrderDto.getMedicineOrderLines().forEach(medicineOrderLineDto -> {
            if(((medicineOrderLineDto.getOrderQuantity() != null ? medicineOrderLineDto.getOrderQuantity() : 0) -
                    (medicineOrderLineDto.getQuantityAllocated() != null ? medicineOrderLineDto.getQuantityAllocated(): 0)) > 0){

                allocateMedicineOrderLine(medicineOrderLineDto);
            }

            totalOrdered.set(totalOrdered.get() + medicineOrderLineDto.getOrderQuantity());
            totalAllocated.set(totalAllocated.get() + (medicineOrderLineDto.getQuantityAllocated() != null ? medicineOrderLineDto.getQuantityAllocated() : 0));
        });

        log.debug("Total Ordered: " + totalOrdered.get());
        log.debug("Total Allocated: " + totalAllocated.get());

        return totalOrdered.get() == totalAllocated.get();
    }

    @Override
    public void deallocateOrder(MedicineOrderDto medicineOrderDto) {
        medicineOrderDto.getMedicineOrderLines().forEach(medicineOrderLineDto -> {

            MedicineInventory medicineInventory = MedicineInventory
                    .builder()
                    .medicineId(medicineOrderLineDto.getMedicineId())
                    .upc(medicineOrderLineDto.getUpc())
                    .quantityOnHand(medicineOrderLineDto.getQuantityAllocated())
                    .build();

            MedicineInventory savedInventory = medicineInventoryRepository.save(medicineInventory);
            log.debug("Saved Inventory for medicine upc: " + savedInventory.getUpc()
                    + "Inventory Id: " + savedInventory.getMedicineId());
        });
    }

    private void allocateMedicineOrderLine(MedicineOrderLineDto medicineOrderLineDto){

        List<MedicineInventory> medicineInventoryList = medicineInventoryRepository.findAllByUpc(medicineOrderLineDto.getUpc());

        medicineInventoryList.forEach(medicineInventory -> {
            int inventory = (medicineInventory.getQuantityOnHand() == null) ? 0 : medicineInventory.getQuantityOnHand();
            int orderQuantity = (medicineOrderLineDto.getOrderQuantity() == null) ? 0 : medicineOrderLineDto.getOrderQuantity();
            int allocatedQuantity = (medicineOrderLineDto.getQuantityAllocated() == null) ? 0 : medicineOrderLineDto.getQuantityAllocated();
            int quantityToAllocate = orderQuantity - allocatedQuantity;

            // In case, we have FULL allocation
            if(inventory >= quantityToAllocate){
                inventory = inventory - quantityToAllocate;
                medicineOrderLineDto.setQuantityAllocated(orderQuantity);
                medicineInventory.setQuantityOnHand(inventory);
                medicineInventoryRepository.save(medicineInventory);
            }
            //In case, we have a PARTIAL allocation
            else if(inventory > 0){
                medicineOrderLineDto.setQuantityAllocated(allocatedQuantity + inventory);
                medicineInventory.setQuantityOnHand(0);
            }

            // In case, we don't have inventory - quantity OnHand
            if(medicineInventory.getQuantityOnHand() == 0){
                medicineInventoryRepository.delete(medicineInventory);
            }
        });
    }
}
