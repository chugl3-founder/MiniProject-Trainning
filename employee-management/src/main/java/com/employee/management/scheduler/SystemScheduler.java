package com.employee.management.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class SystemScheduler {

    private static final Logger log = LoggerFactory.getLogger(SystemScheduler.class);

    // Run every 30 seconds (30000 milliseconds)
    @Scheduled(fixedRate = 30000)
    public void reportSystemStatus() {
        log.info("System running");
    }
}
