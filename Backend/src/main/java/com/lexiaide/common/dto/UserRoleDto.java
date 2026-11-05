package com.lexiaide.common.dto;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

public final class UserRoleDto {

    private UserRoleDto() {
    }

    public record CreateRequest(
            @NotNull UUID userId,
            @NotNull UUID roleId,
            Instant assignedAt
    ) {
    }

    public record UpdateRequest(
            Instant assignedAt
    ) {
    }

    public record Response(
            UUID userId,
            UUID roleId,
            Instant assignedAt
    ) {
    }
}
