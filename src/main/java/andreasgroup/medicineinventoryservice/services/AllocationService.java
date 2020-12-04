package andreasgroup.medicineinventoryservice.services;

import andreasgroup.production.model.MedicineOrderDto;

/**
 * Created on 26/Nov/2020 to microservices-medicine-production
 */
public interface AllocationService {

    Boolean allocateOrder(MedicineOrderDto medicineOrderDto);
    void deallocateOrder(MedicineOrderDto medicineOrderDto);
}
