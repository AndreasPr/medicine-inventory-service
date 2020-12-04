package andreasgroup.medicineinventoryservice.web.mappers;

import andreasgroup.medicineinventoryservice.domain.MedicineInventory;
import andreasgroup.production.model.MedicineInventoryDto;
import org.mapstruct.Mapper;

/**
 * Created on 26/Nov/2020 to microservices-medicine-production
 */
@Mapper(uses = DateMapper.class)
public interface MedicineInventoryMapper {

    MedicineInventory medicineInventoryDtoToMedicineInventory(MedicineInventoryDto medicineInventoryDto);
    MedicineInventoryDto medicineInventoryToMedicineInventoryDto(MedicineInventory medicineInventory);
}
