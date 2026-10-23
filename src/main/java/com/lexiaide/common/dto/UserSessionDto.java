package com.lexiaide.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.UUID;

public final class UserSessionDto {

    private UserSessionDto() {
    }

    public record CreateRequest(
            @NotNull UUID userId,
            @NotBlank @Size(max = 255) String refreshTokenHash,
            @Size(max = 255) String deviceId,
            @Size(max = 45) String ipAddress,
            @NotNull Instant expiresAt
    ) {
    }

    public record UpdateRequest(
            String deviceId,
            String ipAddress,
            Instant expiresAt,
            Instant revokedAt
    ) {
    }

    public record Response(
            UUID id,
            UUID userId,
            String deviceId,
            String ipAddress,
            Instant expiresAt,
            Instant revokedAt
    ) {
    }
}
