package com.lexiaide.common.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public final class StudentProfileDto {

    private StudentProfileDto() {
    }

    public record CreateRequest(
            @NotNull UUID userId,
            @Size(max = 150) String university,
            @Size(max = 150) String fieldOfStudy,
            @Size(max = 50) String studyLevel,
            String bio
    ) {
    }

    public record UpdateRequest(
            @Size(max = 150) String university,
            @Size(max = 150) String fieldOfStudy,
            @Size(max = 50) String studyLevel,
            String bio
    ) {
    }

    public record Response(
            UUID id,
            UUID userId,
            String university,
            String fieldOfStudy,
            String studyLevel,
            String bio
    ) {
    }
}
