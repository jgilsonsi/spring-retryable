package com.jjdev.retry.service;

import com.jjdev.retry.exception.ContinueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ManagerService {

    private static final Logger LOG = LoggerFactory.getLogger(ManagerService.class);
    private RetryService retryService;
    private boolean isProcessAllowed = true;

    @Autowired
    public ManagerService(RetryService retryService) {
        this.retryService = retryService;
    }

    //Wait for 3 loops to see all possible results
    @Scheduled(fixedDelay = 30000, initialDelay = 5000)
    public void execute() {
        if (isProcessAllowed)
            try {
                LOG.info("");
                LOG.info("Start process");

                String result = retryService.process();
                LOG.info("Result answered: {}", result);

                LOG.info("End process");
                if (StringUtils.hasText(result))
                    isProcessAllowed = false;
            } catch (ContinueException e) {
                LOG.info("Exception: {}", e.getMessage());
            }
    }
}
