package com.lexiaide.controller;

import com.lexiaide.common.dto.ProfessionalProfileDto;
import com.lexiaide.service.ProfessionalProfileService;
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
@RequestMapping("/api/v1/professional-profiles")
public class ProfessionalProfileController {

    private final ProfessionalProfileService professionalProfileService;

    public ProfessionalProfileController(ProfessionalProfileService professionalProfileService) {
        this.professionalProfileService = professionalProfileService;
    }

    @PostMapping
    public ResponseEntity<ProfessionalProfileDto.Response> create(@Valid @RequestBody ProfessionalProfileDto.CreateRequest request) {
        ProfessionalProfileDto.Response created = professionalProfileService.create(request);
        return ResponseEntity.created(URI.create("/api/v1/professional-profiles/" + created.id())).body(created);
    }

    @GetMapping("/{id}")
    public ProfessionalProfileDto.Response findById(@PathVariable UUID id) {
        return professionalProfileService.findById(id);
    }

    @GetMapping
    public Page<ProfessionalProfileDto.Response> findAll(
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return professionalProfileService.findAll(pageable);
    }

    @PutMapping("/{id}")
    public ProfessionalProfileDto.Response update(@PathVariable UUID id, @Valid @RequestBody ProfessionalProfileDto.UpdateRequest request) {
        return professionalProfileService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        professionalProfileService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

