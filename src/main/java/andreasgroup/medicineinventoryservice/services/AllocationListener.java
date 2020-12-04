package andreasgroup.medicineinventoryservice.services;

import andreasgroup.medicineinventoryservice.configuration.JmsConfiguration;
import andreasgroup.production.model.events.AllocateOrderRequest;
import andreasgroup.production.model.events.AllocateOrderResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * Created on 26/Nov/2020 to microservices-medicine-production
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class AllocationListener {

    private final AllocationService allocationService;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfiguration.ALLOCATE_ORDER_QUEUE)
    public void listen(AllocateOrderRequest request){

        AllocateOrderResult.AllocateOrderResultBuilder builder = AllocateOrderResult.builder();
        builder.medicineOrderDto(request.getMedicineOrderDto());

        try {
            Boolean allocationResult = allocationService.allocateOrder(request.getMedicineOrderDto());

            if(allocationResult){
                builder.pendingInventory(false);
            }
            else{
                builder.pendingInventory(true);
            }
            builder.allocationError(false);
        } catch (Exception e){
            log.error("Error!!! Allocation failed for the Order with Id: " + request.getMedicineOrderDto().getId());
            builder.allocationError(true);
        }

        jmsTemplate.convertAndSend(JmsConfiguration.ALLOCATE_ORDER_RESPONSE_QUEUE, builder.build());
    }
}
