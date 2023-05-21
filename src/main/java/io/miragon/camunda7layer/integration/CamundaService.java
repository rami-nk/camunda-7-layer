package io.miragon.camunda7layer.integration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.miragon.camunda7layer.service.IPreCheckService;
import io.miragon.camunda7layer.service.PreCheckCommand;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.camunda.bpm.client.ExternalTaskClient;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class CamundaService {

    private final ExternalTaskClient externalTaskClient;

    private final IPreCheckService preCheckService;

    private final CamundaDataMapper camundaDataMapper;

    @PostConstruct
    public void subscribe() {
        externalTaskClient.subscribe("preCheck")
                .lockDuration(1000)
                .handler((externalTask, externalTaskService) -> {

                    // map engine data to PreCheckCommand object
                    final Map<String, Object> data = camundaDataMapper.mapFromEngineData(externalTask.getAllVariablesTyped());
                    final Object mappedData = this.mapInput(PreCheckCommand.class, data);

                    // call use case
                    preCheckService.preCheck((PreCheckCommand) mappedData);

                    // complete task
                    externalTaskService.complete(externalTask);
                })
                .open();
    }

    private Object mapInput(final Class<?> inputType, final Object object) {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.convertValue(object, inputType);
    }
}