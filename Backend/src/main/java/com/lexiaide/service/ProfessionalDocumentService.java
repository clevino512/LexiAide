package com.lexiaide.service;

import com.lexiaide.common.dto.ProfessionalDocumentDto;
import com.lexiaide.common.exception.ResourceNotFoundException;
import com.lexiaide.model.DocumentStatus;
import com.lexiaide.model.ProfessionalDocument;
import com.lexiaide.model.ProfessionalProfile;
import com.lexiaide.repository.ProfessionalDocumentRepository;
import com.lexiaide.repository.ProfessionalProfileRepository;
import java.time.Instant;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfessionalDocumentService {

    private final ProfessionalDocumentRepository professionalDocumentRepository;
    private final ProfessionalProfileRepository professionalProfileRepository;

    public ProfessionalDocumentService(ProfessionalDocumentRepository professionalDocumentRepository, ProfessionalProfileRepository professionalProfileRepository) {
        this.professionalDocumentRepository = professionalDocumentRepository;
        this.professionalProfileRepository = professionalProfileRepository;
    }

    @Transactional
    public ProfessionalDocumentDto.Response create(ProfessionalDocumentDto.CreateRequest request) {
        ProfessionalDocument professionalDocument = new ProfessionalDocument();
        professionalDocument.setProfessional(getProfessionalProfile(request.professionalId()));
        professionalDocument.setDocumentType(request.documentType());
        professionalDocument.setFileUrl(request.fileUrl());
        professionalDocument.setMimeType(request.mimeType());
        professionalDocument.setStatus(request.status() != null ? request.status() : DocumentStatus.PENDING);
        professionalDocument.setUploadedAt(request.uploadedAt() != null ? request.uploadedAt() : Instant.now());
        return toResponse(professionalDocumentRepository.save(professionalDocument));
    }

    @Transactional(readOnly = true)
    public ProfessionalDocumentDto.Response findById(UUID id) {
        return toResponse(getProfessionalDocument(id));
    }

    @Transactional(readOnly = true)
    public Page<ProfessionalDocumentDto.Response> findAll(Pageable pageable) {
        return professionalDocumentRepository.findAll(pageable).map(ProfessionalDocumentService::toResponse);
    }

    @Transactional
    public ProfessionalDocumentDto.Response update(UUID id, ProfessionalDocumentDto.UpdateRequest request) {
        ProfessionalDocument existing = getProfessionalDocument(id);
        existing.setDocumentType(request.documentType());
        existing.setFileUrl(request.fileUrl());
        existing.setMimeType(request.mimeType());
        existing.setStatus(request.status());
        existing.setUploadedAt(request.uploadedAt());
        return toResponse(professionalDocumentRepository.save(existing));
    }

    @Transactional
    public void delete(UUID id) {
        professionalDocumentRepository.delete(getProfessionalDocument(id));
    }

    private ProfessionalDocument getProfessionalDocument(UUID id) {
        return professionalDocumentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document professionnel introuvable : " + id));
    }

    private ProfessionalProfile getProfessionalProfile(UUID id) {
        return professionalProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profil professionnel introuvable : " + id));
    }

    private static ProfessionalDocumentDto.Response toResponse(ProfessionalDocument professionalDocument) {
        return new ProfessionalDocumentDto.Response(
                professionalDocument.getId(),
                professionalDocument.getProfessional() != null ? professionalDocument.getProfessional().getId() : null,
                professionalDocument.getDocumentType(),
                professionalDocument.getFileUrl(),
                professionalDocument.getMimeType(),
                professionalDocument.getStatus(),
                professionalDocument.getUploadedAt()
        );
    }
}

