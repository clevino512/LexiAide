package com.lexiaide.modules.professional_types;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessionalTypeRepository extends JpaRepository<ProfessionalType, UUID> {
    Optional<ProfessionalType> findByCode(String code);
    boolean existsByCode(String code);
}
