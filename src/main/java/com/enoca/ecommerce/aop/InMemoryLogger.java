package com.enoca.ecommerce.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryLogger {
    private final List<String> logs = new ArrayList<>();
    private final Logger logger = LoggerFactory.getLogger(InMemoryLogger.class);

    public void log(String message) {
        logs.add(message);
        logger.info(message); // This logs the message using SLF4J
    }

    public List<String> getLogs() {
        return new ArrayList<>(logs); // Return a copy of the logs
    }
}
