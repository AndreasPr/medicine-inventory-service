package andreasgroup.production.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Created on 26/Nov/2020 to microservices-medicine-production
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicineInventoryDto {

    private UUID id;
    private OffsetDateTime createdDate;
    private OffsetDateTime lastModifiedDate;
    private UUID medicineId;
    private String upc;
    private Integer quantityOnHand;
}
