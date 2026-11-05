package com.lexiaide.service;

import com.lexiaide.common.dto.ProfessionalProfileDto;
import com.lexiaide.common.exception.ResourceNotFoundException;
import com.lexiaide.model.ProfessionalProfile;
import com.lexiaide.model.ProfessionalType;
import com.lexiaide.model.User;
import com.lexiaide.model.VerificationStatus;
import com.lexiaide.repository.ProfessionalProfileRepository;
import com.lexiaide.repository.ProfessionalTypeRepository;
import com.lexiaide.repository.UserRepository;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfessionalProfileService {

    private final ProfessionalProfileRepository professionalProfileRepository;
    private final UserRepository userRepository;
    private final ProfessionalTypeRepository professionalTypeRepository;

    public ProfessionalProfileService(ProfessionalProfileRepository professionalProfileRepository, UserRepository userRepository, ProfessionalTypeRepository professionalTypeRepository) {
        this.professionalProfileRepository = professionalProfileRepository;
        this.userRepository = userRepository;
        this.professionalTypeRepository = professionalTypeRepository;
    }

    @Transactional
    public ProfessionalProfileDto.Response create(ProfessionalProfileDto.CreateRequest request) {
        ProfessionalProfile professionalProfile = new ProfessionalProfile();
        professionalProfile.setUser(getUser(request.userId()));
        professionalProfile.setProfessionalType(request.professionalTypeId() != null ? getProfessionalType(request.professionalTypeId()) : null);
        professionalProfile.setLicenseNumber(request.licenseNumber());
        professionalProfile.setOfficeName(request.officeName());
        professionalProfile.setCity(request.city());
        professionalProfile.setOfficeAddress(request.officeAddress());
        professionalProfile.setBio(request.bio());
        professionalProfile.setVerificationStatus(request.verificationStatus() != null ? request.verificationStatus() : VerificationStatus.PENDING);
        return toResponse(professionalProfileRepository.save(professionalProfile));
    }

    @Transactional(readOnly = true)
    public ProfessionalProfileDto.Response findById(UUID id) {
        return toResponse(getProfessionalProfile(id));
    }

    @Transactional(readOnly = true)
    public Page<ProfessionalProfileDto.Response> findAll(Pageable pageable) {
        return professionalProfileRepository.findAll(pageable).map(ProfessionalProfileService::toResponse);
    }

    @Transactional
    public ProfessionalProfileDto.Response update(UUID id, ProfessionalProfileDto.UpdateRequest request) {
        ProfessionalProfile existing = getProfessionalProfile(id);
        existing.setProfessionalType(request.professionalTypeId() != null ? getProfessionalType(request.professionalTypeId()) : null);
        existing.setLicenseNumber(request.licenseNumber());
        existing.setOfficeName(request.officeName());
        existing.setCity(request.city());
        existing.setOfficeAddress(request.officeAddress());
        existing.setBio(request.bio());
        existing.setVerificationStatus(request.verificationStatus());
        return toResponse(professionalProfileRepository.save(existing));
    }

    @Transactional
    public void delete(UUID id) {
        professionalProfileRepository.delete(getProfessionalProfile(id));
    }

    private ProfessionalProfile getProfessionalProfile(UUID id) {
        return professionalProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profil professionnel introuvable : " + id));
    }

    private User getUser(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur introuvable : " + id));
    }

    private ProfessionalType getProfessionalType(UUID id) {
        return professionalTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Type de professionnel introuvable : " + id));
    }

    private static ProfessionalProfileDto.Response toResponse(ProfessionalProfile professionalProfile) {
        return new ProfessionalProfileDto.Response(
                professionalProfile.getId(),
                professionalProfile.getUser() != null ? professionalProfile.getUser().getId() : null,
                professionalProfile.getProfessionalType() != null ? professionalProfile.getProfessionalType().getId() : null,
                professionalProfile.getLicenseNumber(),
                professionalProfile.getOfficeName(),
                professionalProfile.getCity(),
                professionalProfile.getOfficeAddress(),
                professionalProfile.getBio(),
                professionalProfile.getVerificationStatus()
        );
    }
}

