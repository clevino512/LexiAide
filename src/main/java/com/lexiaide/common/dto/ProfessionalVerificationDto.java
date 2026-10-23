package com.lexiaide.common.dto;

import com.lexiaide.model.VerificationStatus;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

public final class ProfessionalVerificationDto {

    private ProfessionalVerificationDto() {
    }

    public record CreateRequest(
            @NotNull UUID professionalId,
            UUID reviewedById,
            VerificationStatus status,
            String rejectionReason,
            Instant submittedAt,
            Instant reviewedAt
    ) {
    }

    public record UpdateRequest(
            UUID reviewedById,
            VerificationStatus status,
            String rejectionReason,
            Instant submittedAt,
            Instant reviewedAt
    ) {
    }

    public record Response(
            UUID id,
            UUID professionalId,
            UUID reviewedById,
            VerificationStatus status,
            String rejectionReason,
            Instant submittedAt,
            Instant reviewedAt
    ) {
    }
}
