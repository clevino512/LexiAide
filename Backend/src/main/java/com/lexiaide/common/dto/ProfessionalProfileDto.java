package com.lexiaide.common.dto;

import com.lexiaide.model.VerificationStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public final class ProfessionalProfileDto {

    private ProfessionalProfileDto() {
    }

    public record CreateRequest(
            @NotNull UUID userId,
            UUID professionalTypeId,
            @Size(max = 100) String licenseNumber,
            @Size(max = 150) String officeName,
            @Size(max = 100) String city,
            String officeAddress,
            String bio,
            VerificationStatus verificationStatus
    ) {
    }

    public record UpdateRequest(
            UUID professionalTypeId,
            @Size(max = 100) String licenseNumber,
            @Size(max = 150) String officeName,
            @Size(max = 100) String city,
            String officeAddress,
            String bio,
            VerificationStatus verificationStatus
    ) {
    }

    public record Response(
            UUID id,
            UUID userId,
            UUID professionalTypeId,
            String licenseNumber,
            String officeName,
            String city,
            String officeAddress,
            String bio,
            VerificationStatus verificationStatus
    ) {
    }
}
