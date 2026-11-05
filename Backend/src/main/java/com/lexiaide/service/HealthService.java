package com.lexiaide.service;

import com.lexiaide.common.dto.HealthResponse;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class HealthService {

    public HealthResponse getHealth() {
        return new HealthResponse("UP", Instant.now());
    }
}
