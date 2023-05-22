package io.miragon.camunda7layer.worker;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.camunda.bpm.client.ExternalTaskClient;

import java.util.Map;

public abstract class AbstractWorker {

    private final ExternalTaskClient externalTaskClient;
    private final CamundaDataMapper camundaDataMapper;
    private final String topic;

    public AbstractWorker(ExternalTaskClient externalTaskClient, CamundaDataMapper camundaDataMapper, String topic) {
        this.externalTaskClient = externalTaskClient;
        this.camundaDataMapper = camundaDataMapper;
        this.topic = topic;
    }

    public abstract void execute(Map<String, Object> data);

    @PostConstruct
    public void _subscribe() {
        externalTaskClient.subscribe(topic)
                .lockDuration(1000)
                .handler((externalTask, externalTaskService) -> {

                    // map engine data to PreCheckCommand object
                    final Map<String, Object> data = camundaDataMapper.mapFromEngineData(externalTask.getAllVariablesTyped());

                    // execute use case
                    execute(data);

                    // complete task
                    externalTaskService.complete(externalTask);
                })
                .open();
    }

    protected Object mapInput(final Class<?> inputType, final Object object) {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.convertValue(object, inputType);
    }
}
