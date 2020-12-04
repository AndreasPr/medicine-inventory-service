package andreasgroup.medicineinventoryservice.services;

import andreasgroup.medicineinventoryservice.configuration.JmsConfiguration;
import andreasgroup.production.model.events.DeallocateOrderRequest;
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
public class DeallocationListener {

    private final AllocationService allocationService;

    @JmsListener(destination = JmsConfiguration.DEALLOCATE_ORDER_QUEUE)
    public void listen(DeallocateOrderRequest request){
        allocationService.deallocateOrder(request.getMedicineOrderDto());
    }

}
