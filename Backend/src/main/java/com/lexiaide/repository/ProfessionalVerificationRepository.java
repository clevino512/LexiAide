package com.lexiaide.repository;

import com.lexiaide.model.*;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessionalVerificationRepository extends JpaRepository<ProfessionalVerification, UUID> {
    List<ProfessionalVerification> findByProfessionalId(UUID professionalId);
    List<ProfessionalVerification> findByStatus(VerificationStatus status);
    List<ProfessionalVerification> findByReviewedById(UUID reviewedById);
}
