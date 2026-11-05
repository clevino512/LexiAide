package com.lexiaide.controller;

import com.lexiaide.common.dto.ProfessionalVerificationDto;
import com.lexiaide.service.ProfessionalVerificationService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/professional-verifications")
public class ProfessionalVerificationController {

    private final ProfessionalVerificationService professionalVerificationService;

    public ProfessionalVerificationController(ProfessionalVerificationService professionalVerificationService) {
        this.professionalVerificationService = professionalVerificationService;
    }

    @PostMapping
    public ResponseEntity<ProfessionalVerificationDto.Response> create(@Valid @RequestBody ProfessionalVerificationDto.CreateRequest request) {
        ProfessionalVerificationDto.Response created = professionalVerificationService.create(request);
        return ResponseEntity.created(URI.create("/api/v1/professional-verifications/" + created.id())).body(created);
    }

    @GetMapping("/{id}")
    public ProfessionalVerificationDto.Response findById(@PathVariable UUID id) {
        return professionalVerificationService.findById(id);
    }

    @GetMapping
    public Page<ProfessionalVerificationDto.Response> findAll(
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return professionalVerificationService.findAll(pageable);
    }

    @PutMapping("/{id}")
    public ProfessionalVerificationDto.Response update(@PathVariable UUID id, @Valid @RequestBody ProfessionalVerificationDto.UpdateRequest request) {
        return professionalVerificationService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        professionalVerificationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

