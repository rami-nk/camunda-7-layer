package io.miragon.camunda7layer.service.interfaces;

import javax.annotation.PostConstruct;

public interface IProcessDeploymentService {
    @PostConstruct
    void deployProcess();
}