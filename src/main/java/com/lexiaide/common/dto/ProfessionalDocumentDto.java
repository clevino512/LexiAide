package com.lexiaide.common.dto;

import com.lexiaide.model.DocumentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.UUID;

public final class ProfessionalDocumentDto {

    private ProfessionalDocumentDto() {
    }

    public record CreateRequest(
            @NotNull UUID professionalId,
            @NotBlank @Size(max = 100) String documentType,
            @Size(max = 2048) String fileUrl,
            @Size(max = 100) String mimeType,
            DocumentStatus status,
            Instant uploadedAt
    ) {
    }

    public record UpdateRequest(
            String documentType,
            String fileUrl,
            String mimeType,
            DocumentStatus status,
            Instant uploadedAt
    ) {
    }

    public record Response(
            UUID id,
            UUID professionalId,
            String documentType,
            String fileUrl,
            String mimeType,
            DocumentStatus status,
            Instant uploadedAt
    ) {
    }
}
