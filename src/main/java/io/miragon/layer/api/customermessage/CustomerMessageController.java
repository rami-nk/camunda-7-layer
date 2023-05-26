package io.miragon.layer.api.customermessage;

import lombok.AllArgsConstructor;
import org.camunda.community.rest.client.api.MessageApi;
import org.camunda.community.rest.client.dto.CorrelationMessageDto;
import org.camunda.community.rest.client.dto.VariableValueDto;
import org.camunda.community.rest.client.invoker.ApiException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("")
@AllArgsConstructor
public class CustomerMessageController {

    private final MessageApi messageApi;

    @PostMapping("/customer-message")
    public void customerMessage(@RequestBody CustomerMessageRequestDto customerMessageRequestDto) throws ApiException {
        var correlationMessageDto = new CorrelationMessageDto();
        correlationMessageDto.setMessageName("BookRequestConfirmation");

        var variables = new HashMap<String, VariableValueDto>();
        var variableValueDto = new VariableValueDto();
        variableValueDto.setValue(customerMessageRequestDto);

        correlationMessageDto.setProcessVariables(variables);
        messageApi.deliverMessage(correlationMessageDto);
    }
}
