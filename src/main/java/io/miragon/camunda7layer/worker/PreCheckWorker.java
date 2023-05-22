package io.miragon.camunda7layer.worker;

import io.miragon.camunda7layer.service.IPreCheckService;
import io.miragon.camunda7layer.service.PreCheckCommand;
import org.camunda.bpm.client.ExternalTaskClient;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PreCheckWorker extends AbstractWorker {

    private final IPreCheckService preCheckService;

    public PreCheckWorker(IPreCheckService preCheckService, CamundaDataMapper camundaDataMapper, ExternalTaskClient externalTaskClient) {
        super(externalTaskClient, camundaDataMapper, "preCheck");
        this.preCheckService = preCheckService;
    }

    @Override
    public void execute(Map<String, Object> data) {
        // map engine data to PreCheckCommand object
        final Object mappedData = this.mapInput(PreCheckCommand.class, data);

        // call use case
        preCheckService.preCheck((PreCheckCommand) mappedData);
    }
}