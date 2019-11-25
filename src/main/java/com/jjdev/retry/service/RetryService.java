package com.jjdev.retry.service;

import com.jjdev.retry.exception.ContinueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.RetryException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class RetryService {

    private static final Logger LOG = LoggerFactory.getLogger(RetryService.class);
    int step = 0;

    @Retryable(value = RetryException.class, maxAttempts = 3, backoff = @Backoff(delay = 5000))
    public String process() throws ContinueException {
        step++;

        LOG.info("Step: {}", step);
        LOG.info("Processing request...");

        switch (step) {
            case 2:
                throw new ContinueException("Stop the process without more attempts");
            case 7:
                return "success ;)";
            default:
                throw new RetryException("It will process all attempts");
        }
    }

    @Recover
    private String couldNotProcess(RetryException e) {
        LOG.error("Error while processing request. Giving up...");
        LOG.error("Error: {}", e.getMessage());
        return null;
    }

    @Recover
    private String couldNotProcess(ContinueException e) {
        LOG.error("Error while processing request. Stopping...");
        LOG.error("Error: {}", e.getMessage());
        return null;
    }
}
