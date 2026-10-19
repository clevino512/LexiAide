package com.lexiaide.common.dto;

import java.time.Instant;

public record HealthResponse(String status, Instant timestamp) {
}
