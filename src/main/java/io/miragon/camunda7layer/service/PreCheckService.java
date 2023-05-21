package io.miragon.camunda7layer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PreCheckService implements IPreCheckService {

    @Override
    public void preCheck(PreCheckCommand preCheckCommand) {
        log.info("Checking credit request for {} {} with age {} and income {}",
                preCheckCommand.getFirstname(),
                preCheckCommand.getLastname(),
                preCheckCommand.getAge(),
                preCheckCommand.getIncome()
        );
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}