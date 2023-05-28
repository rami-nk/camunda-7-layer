package io.miragon.layer.api;

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
public class FlowableProcessDeploymentService {

    private final RepositoryService repositoryService;

    private final RuntimeService runtimeService;

    @PostConstruct
    public void deployProcess() {
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("bpmn/book-request.bpmn20.xml")
                .deploy();

        log.info("Deployed: {}", deployment.getId());

        Map<String, Object> variables = new HashMap<>();
        variables.put("title", "Harry Potter");
        variables.put("author", "J.K. Rowling");
        variables.put("isbn", "978-3-16-148410-0");

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("book-request", variables);

        log.info("Process \"{}\" started", processInstance.getProcessDefinitionName());
    }
}