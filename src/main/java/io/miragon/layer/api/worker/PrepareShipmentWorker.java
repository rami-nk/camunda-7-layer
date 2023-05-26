package io.miragon.layer.api.worker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.miragon.layer.service.prepareshipment.PrepareShipmentData;
import io.miragon.layer.service.prepareshipment.PrepareShipmentService;
import lombok.AllArgsConstructor;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.value.TypedValue;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
@ExternalTaskSubscription("prepareShipment")
public class PrepareShipmentWorker implements ExternalTaskHandler {

    private final PrepareShipmentService prepareShipmentService;

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        // map engine data to PreCheckCommand object
        final Map<String, Object> data = mapFromEngineData(externalTask.getAllVariablesTyped());
        final PrepareShipmentData mappedData = this.mapInput(PrepareShipmentData.class, data);

        // execute use case
        prepareShipmentService.prepareShipment(mappedData);

        // complete task
        externalTaskService.complete(externalTask);
    }

    private Map<String, Object> mapFromEngineData(final VariableMap variables) {
        final Map<String, Object> data = new HashMap<>();
        variables.keySet().forEach(key -> {
            final TypedValue value = variables.getValueTyped(key);
            if (value.getType().getName().equals("json")) {
                try {
                    data.put(key, this.mapFromEngineData(value.getValue()));
                } catch (final JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            } else {
                data.put(key, value.getValue());
            }
        });
        return data;
    }

    public Object mapFromEngineData(final Object value) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        if (value.toString().startsWith("[")) {
            return mapper.readValue(value.toString(), new TypeReference<List<?>>() {
            });
        }
        return mapper.readValue(value.toString(), new TypeReference<Map<String, Object>>() {
        });
    }

    private <T> T mapInput(final Class<T> inputType, final Object object) {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.convertValue(object, inputType);
    }
}
