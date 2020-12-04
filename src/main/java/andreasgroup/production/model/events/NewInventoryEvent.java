package andreasgroup.production.model.events;

import lombok.NoArgsConstructor;

/**
 * Created on 26/Nov/2020 to microservices-medicine-production
 */
@NoArgsConstructor
public class NewInventoryEvent extends MedicineEvent {
    public NewInventoryEvent(MedicineDto medicineDto){
        super(medicineDto);
    }
}
