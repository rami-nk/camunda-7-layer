package io.miragon.camunda7layer.worker;

import io.miragon.camunda7layer.service.interfaces.IPreCheckService;
import io.miragon.camunda7layer.service.PreCheckData;
import lombok.AllArgsConstructor;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service("preCheckDelegate")
@AllArgsConstructor
public class PreCheckDelegate implements JavaDelegate {


    private final IPreCheckService preCheckService;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        preCheckService.preCheck(new PreCheckData(
                (String) delegateExecution.getVariable("firstname"),
                (String) delegateExecution.getVariable("lastname"),
                (Integer) delegateExecution.getVariable("age"),
                (Integer) delegateExecution.getVariable("income")
        ));
    }
}