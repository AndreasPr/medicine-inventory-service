package andreasgroup.medicineinventoryservice.repositories;

import andreasgroup.medicineinventoryservice.domain.MedicineInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Created on 26/Nov/2020 to microservices-medicine-production
 */
public interface MedicineInventoryRepository extends JpaRepository<MedicineInventory, UUID> {

    List<MedicineInventory> findAllByMedicineId(UUID medicineId);
    List<MedicineInventory> findAllByUpc(String upc);
}
