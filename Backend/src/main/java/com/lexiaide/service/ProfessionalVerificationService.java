package com.lexiaide.service;

import com.lexiaide.common.dto.ProfessionalVerificationDto;
import com.lexiaide.common.exception.ResourceNotFoundException;
import com.lexiaide.model.ProfessionalProfile;
import com.lexiaide.model.ProfessionalVerification;
import com.lexiaide.model.User;
import com.lexiaide.model.VerificationStatus;
import com.lexiaide.repository.ProfessionalProfileRepository;
import com.lexiaide.repository.ProfessionalVerificationRepository;
import com.lexiaide.repository.UserRepository;
import java.time.Instant;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfessionalVerificationService {

    private final ProfessionalVerificationRepository professionalVerificationRepository;
    private final ProfessionalProfileRepository professionalProfileRepository;
    private final UserRepository userRepository;

    public ProfessionalVerificationService(ProfessionalVerificationRepository professionalVerificationRepository, ProfessionalProfileRepository professionalProfileRepository, UserRepository userRepository) {
        this.professionalVerificationRepository = professionalVerificationRepository;
        this.professionalProfileRepository = professionalProfileRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ProfessionalVerificationDto.Response create(ProfessionalVerificationDto.CreateRequest request) {
        ProfessionalVerification professionalVerification = new ProfessionalVerification();
        professionalVerification.setProfessional(getProfessionalProfile(request.professionalId()));
        professionalVerification.setReviewedBy(request.reviewedById() != null ? getUser(request.reviewedById()) : null);
        professionalVerification.setStatus(request.status() != null ? request.status() : VerificationStatus.PENDING);
        professionalVerification.setRejectionReason(request.rejectionReason());
        professionalVerification.setSubmittedAt(request.submittedAt() != null ? request.submittedAt() : Instant.now());
        professionalVerification.setReviewedAt(request.reviewedAt());
        return toResponse(professionalVerificationRepository.save(professionalVerification));
    }

    @Transactional(readOnly = true)
    public ProfessionalVerificationDto.Response findById(UUID id) {
        return toResponse(getProfessionalVerification(id));
    }

    @Transactional(readOnly = true)
    public Page<ProfessionalVerificationDto.Response> findAll(Pageable pageable) {
        return professionalVerificationRepository.findAll(pageable).map(ProfessionalVerificationService::toResponse);
    }

    @Transactional
    public ProfessionalVerificationDto.Response update(UUID id, ProfessionalVerificationDto.UpdateRequest request) {
        ProfessionalVerification existing = getProfessionalVerification(id);
        existing.setReviewedBy(request.reviewedById() != null ? getUser(request.reviewedById()) : null);
        existing.setStatus(request.status());
        existing.setRejectionReason(request.rejectionReason());
        existing.setSubmittedAt(request.submittedAt());
        existing.setReviewedAt(request.reviewedAt());
        return toResponse(professionalVerificationRepository.save(existing));
    }

    @Transactional
    public void delete(UUID id) {
        professionalVerificationRepository.delete(getProfessionalVerification(id));
    }

    private ProfessionalVerification getProfessionalVerification(UUID id) {
        return professionalVerificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Verification professionnelle introuvable : " + id));
    }

    private ProfessionalProfile getProfessionalProfile(UUID id) {
        return professionalProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profil professionnel introuvable : " + id));
    }

    private User getUser(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur introuvable : " + id));
    }

    private static ProfessionalVerificationDto.Response toResponse(ProfessionalVerification professionalVerification) {
        return new ProfessionalVerificationDto.Response(
                professionalVerification.getId(),
                professionalVerification.getProfessional() != null ? professionalVerification.getProfessional().getId() : null,
                professionalVerification.getReviewedBy() != null ? professionalVerification.getReviewedBy().getId() : null,
                professionalVerification.getStatus(),
                professionalVerification.getRejectionReason(),
                professionalVerification.getSubmittedAt(),
                professionalVerification.getReviewedAt()
        );
    }
}

