package io.miragon.camunda7layer.service;

import io.miragon.camunda7layer.service.interfaces.IProcessDeploymentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class ProcessDeploymentService implements IProcessDeploymentService {

    private final RepositoryService repositoryService;

    private final RuntimeService runtimeService;

    @Override
    @PostConstruct
    public void deployProcess() {
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("preCheck.bpmn20.xml")
                .deploy();

        log.info("Deployed: {}", deployment.getId());

        Map<String, Object> variables = new HashMap<>();
        variables.put("firstname", "FirstName");
        variables.put("lastname", "LastName");
        variables.put("age", 20);
        variables.put("income", 200000);

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("precheck", variables);

        log.info("Process \"{}\" started", processInstance.getProcessDefinitionName());
    }
}