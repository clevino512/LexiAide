package com.lexiaide.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public final class RoleDto {

    private RoleDto() {
    }

    public record CreateRequest(
            @NotBlank @Size(max = 100) String code,
            @NotBlank @Size(max = 255) String name
    ) {
    }

    public record UpdateRequest(
            @NotBlank @Size(max = 100) String code,
            @NotBlank @Size(max = 255) String name
    ) {
    }

    public record Response(
            UUID id,
            String code,
            String name
    ) {
    }
}
