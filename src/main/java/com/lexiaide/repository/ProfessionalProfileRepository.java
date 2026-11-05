package com.lexiaide.repository;

import com.lexiaide.model.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessionalProfileRepository extends JpaRepository<ProfessionalProfile, UUID> {
    Optional<ProfessionalProfile> findByUserId(UUID userId);
    Optional<ProfessionalProfile> findByLicenseNumber(String licenseNumber);
    List<ProfessionalProfile> findByVerificationStatus(VerificationStatus verificationStatus);
    List<ProfessionalProfile> findByCityIgnoreCase(String city);
}
