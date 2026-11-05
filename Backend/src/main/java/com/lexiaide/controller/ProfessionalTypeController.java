package com.lexiaide.controller;

import com.lexiaide.common.dto.ProfessionalTypeDto;
import com.lexiaide.service.ProfessionalTypeService;
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
@RequestMapping("/api/v1/professional-types")
public class ProfessionalTypeController {

    private final ProfessionalTypeService professionalTypeService;

    public ProfessionalTypeController(ProfessionalTypeService professionalTypeService) {
        this.professionalTypeService = professionalTypeService;
    }

    @PostMapping
    public ResponseEntity<ProfessionalTypeDto.Response> create(@Valid @RequestBody ProfessionalTypeDto.CreateRequest request) {
        ProfessionalTypeDto.Response created = professionalTypeService.create(request);
        return ResponseEntity.created(URI.create("/api/v1/professional-types/" + created.id())).body(created);
    }

    @GetMapping("/{id}")
    public ProfessionalTypeDto.Response findById(@PathVariable UUID id) {
        return professionalTypeService.findById(id);
    }

    @GetMapping
    public Page<ProfessionalTypeDto.Response> findAll(
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return professionalTypeService.findAll(pageable);
    }

    @PutMapping("/{id}")
    public ProfessionalTypeDto.Response update(@PathVariable UUID id, @Valid @RequestBody ProfessionalTypeDto.UpdateRequest request) {
        return professionalTypeService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        professionalTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

