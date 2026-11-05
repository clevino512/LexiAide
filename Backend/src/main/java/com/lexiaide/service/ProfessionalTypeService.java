package com.lexiaide.service;

import com.lexiaide.common.dto.ProfessionalTypeDto;
import com.lexiaide.common.exception.ResourceNotFoundException;
import com.lexiaide.model.ProfessionalType;
import com.lexiaide.repository.ProfessionalTypeRepository;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfessionalTypeService {

    private final ProfessionalTypeRepository professionalTypeRepository;

    public ProfessionalTypeService(ProfessionalTypeRepository professionalTypeRepository) {
        this.professionalTypeRepository = professionalTypeRepository;
    }

    @Transactional
    public ProfessionalTypeDto.Response create(ProfessionalTypeDto.CreateRequest request) {
        ProfessionalType professionalType = new ProfessionalType();
        professionalType.setCode(request.code());
        professionalType.setName(request.name());
        return toResponse(professionalTypeRepository.save(professionalType));
    }

    @Transactional(readOnly = true)
    public ProfessionalTypeDto.Response findById(UUID id) {
        return toResponse(getProfessionalType(id));
    }

    @Transactional(readOnly = true)
    public Page<ProfessionalTypeDto.Response> findAll(Pageable pageable) {
        return professionalTypeRepository.findAll(pageable).map(ProfessionalTypeService::toResponse);
    }

    @Transactional
    public ProfessionalTypeDto.Response update(UUID id, ProfessionalTypeDto.UpdateRequest request) {
        ProfessionalType existing = getProfessionalType(id);
        existing.setCode(request.code());
        existing.setName(request.name());
        return toResponse(professionalTypeRepository.save(existing));
    }

    @Transactional
    public void delete(UUID id) {
        professionalTypeRepository.delete(getProfessionalType(id));
    }

    private ProfessionalType getProfessionalType(UUID id) {
        return professionalTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Type de professionnel introuvable : " + id));
    }

    private static ProfessionalTypeDto.Response toResponse(ProfessionalType professionalType) {
        return new ProfessionalTypeDto.Response(professionalType.getId(), professionalType.getCode(), professionalType.getName());
    }
}

