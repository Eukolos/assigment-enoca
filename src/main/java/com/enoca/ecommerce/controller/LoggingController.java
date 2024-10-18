package com.enoca.ecommerce.controller;

import com.enoca.ecommerce.aop.InMemoryLogger;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LoggingController {

    private final InMemoryLogger inMemoryLogger;

    @Operation(summary = "Retrieve Logs", description = "Fetches the list of logs stored in memory. In memory is not good for production. But This is only showcase purposes.")
    @GetMapping("/logs")
    public List<String> getLogs() {
        return inMemoryLogger.getLogs();
    }
}