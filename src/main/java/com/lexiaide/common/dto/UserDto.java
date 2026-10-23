package com.lexiaide.common.dto;

import com.lexiaide.model.PreferredLanguage;
import com.lexiaide.model.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.UUID;

public final class UserDto {

    private UserDto() {
    }

    public record CreateRequest(
            @NotBlank @Size(max = 100) String firstName,
            @NotBlank @Size(max = 100) String lastName,
            @NotBlank @Email @Size(max = 255) String email,
            @NotBlank @Size(max = 50) String phone,
            @NotBlank @Size(max = 255) String passwordHash,
            @Size(max = 2048) String photoUrl,
            PreferredLanguage preferredLanguage
    ) {
    }

    public record UpdateRequest(
            @NotBlank @Size(max = 100) String firstName,
            @NotBlank @Size(max = 100) String lastName,
            @NotBlank @Email @Size(max = 255) String email,
            @NotBlank @Size(max = 50) String phone,
            @Size(max = 2048) String photoUrl,
            PreferredLanguage preferredLanguage,
            UserStatus status
    ) {
    }

    public record Response(
            UUID id,
            String firstName,
            String lastName,
            String email,
            String phone,
            String photoUrl,
            PreferredLanguage preferredLanguage,
            UserStatus status,
            Instant emailVerifiedAt,
            Instant createdAt
    ) {
    }
}
