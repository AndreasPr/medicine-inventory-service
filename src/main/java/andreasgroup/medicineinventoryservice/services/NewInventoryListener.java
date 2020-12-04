package andreasgroup.medicineinventoryservice.services;

import andreasgroup.medicineinventoryservice.configuration.JmsConfiguration;
import andreasgroup.medicineinventoryservice.domain.MedicineInventory;
import andreasgroup.medicineinventoryservice.repositories.MedicineInventoryRepository;
import andreasgroup.production.model.events.NewInventoryEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Created on 26/Nov/2020 to microservices-medicine-production
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class NewInventoryListener {

    private final MedicineInventoryRepository medicineInventoryRepository;

    @JmsListener(destination = JmsConfiguration.NEW_INVENTORY_QUEUE)
    public void listen(NewInventoryEvent event){
        log.debug("Got inventory: " + event.toString());

        medicineInventoryRepository.save(MedicineInventory.builder()
                .medicineId(event.getMedicineDto().getId())
                .upc(event.getMedicineDto().getUpc())
                .quantityOnHand(event.getMedicineDto().getQuantityOnHand())
                .build());
    }
}
