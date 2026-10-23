package com.lexiaide.repository;

import com.lexiaide.model.*;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessionalDocumentRepository extends JpaRepository<ProfessionalDocument, UUID> {
    List<ProfessionalDocument> findByProfessionalId(UUID professionalId);
    List<ProfessionalDocument> findByStatus(DocumentStatus status);
}
