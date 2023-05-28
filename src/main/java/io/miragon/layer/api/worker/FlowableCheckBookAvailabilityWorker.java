package io.miragon.layer.api.worker;

import io.miragon.layer.service.bookavailablity.BookAvailabilityData;
import io.miragon.layer.service.bookavailablity.CheckBookAvailabilityService;
import lombok.AllArgsConstructor;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("checkBookAvailabilityWorker")
@AllArgsConstructor
public class FlowableCheckBookAvailabilityWorker implements JavaDelegate {

    private final CheckBookAvailabilityService checkBookAvailabilityService;

    @Override
    public void execute(DelegateExecution execution) {
        var command = new BookAvailabilityData();
        command.setTitle((String) execution.getVariable("title"));
        command.setAuthor((String) execution.getVariable("author"));
        command.setIsbn((String) execution.getVariable("isbn"));
        checkBookAvailabilityService.checkAvailability(command);
    }
}