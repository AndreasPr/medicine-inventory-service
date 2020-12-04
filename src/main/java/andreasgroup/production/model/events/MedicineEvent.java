package andreasgroup.production.model.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created on 26/Nov/2020 to microservices-medicine-production
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicineEvent implements Serializable {

    static final long serialVersionUID = 1281755427988163883L;
    private MedicineDto medicineDto;
}
