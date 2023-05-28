package io.miragon.layer.api.worker;

import io.miragon.layer.service.prepareshipment.PrepareShipmentData;
import io.miragon.layer.service.prepareshipment.PrepareShipmentService;
import lombok.AllArgsConstructor;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("prepareShipmentWorker")
@AllArgsConstructor
public class FlowablePrepareShipmentWorker implements JavaDelegate {

    private final PrepareShipmentService prepareShipmentService;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        var data = new PrepareShipmentData();
        data.setFirstname((String) delegateExecution.getVariable("firstname"));
        data.setLastname((String) delegateExecution.getVariable("lastname"));
        data.setAddress((String) delegateExecution.getVariable("address"));
        prepareShipmentService.prepareShipment(data);
    }
}