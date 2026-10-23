package com.lexiaide.modules.professional_documents;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessionalDocumentRepository extends JpaRepository<ProfessionalDocument, UUID> {
    List<ProfessionalDocument> findByProfessionalId(UUID professionalId);
    List<ProfessionalDocument> findByStatus(DocumentStatus status);
}
