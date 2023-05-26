package io.miragon.layer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PreCheckService {

    public void preCheck(PreCheckData preCheckData) {
        log.info("Checking credit request for {} {} with age {} and income {}",
                preCheckData.getFirstname(),
                preCheckData.getLastname(),
                preCheckData.getAge(),
                preCheckData.getIncome()
        );
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}