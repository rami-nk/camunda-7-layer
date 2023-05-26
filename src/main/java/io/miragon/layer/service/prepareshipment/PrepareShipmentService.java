package io.miragon.layer.service.prepareshipment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PrepareShipmentService {

    public void prepareShipment(PrepareShipmentData prepareShipmentData) {
        log.info("Preparing shipment for {} {} with address {}",
                prepareShipmentData.getFirstname(),
                prepareShipmentData.getLastname(),
                prepareShipmentData.getAddress()
        );
    }
}
