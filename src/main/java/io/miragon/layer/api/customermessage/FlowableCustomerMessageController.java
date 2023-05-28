package io.miragon.layer.api.customermessage;

import lombok.AllArgsConstructor;
import org.flowable.engine.RuntimeService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping
@AllArgsConstructor
public class FlowableCustomerMessageController {

    private final RuntimeService runtimeService;

    @PostMapping("/customer-message")
    public void customerMessage(@RequestBody CustomerMessageRequestDto customerMessageRequestDto) {
        Map<String, Object> map = Map.of(
                "firstname", customerMessageRequestDto.getFirstname(),
                "lastname", customerMessageRequestDto.getLastname(),
                "address", customerMessageRequestDto.getAddress()
        );

        // get the first execution with the message event subscription
        var executions = runtimeService.createExecutionQuery()
                .messageEventSubscriptionName("BookRequestConfirmation")
                .list();

        if (executions.isEmpty()) {
            throw new RuntimeException("No executions found");
        }

        var executionId = executions.get(0).getId();

        runtimeService.messageEventReceived("BookRequestConfirmation", executionId, map);
    }
}