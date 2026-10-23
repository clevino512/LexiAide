package com.lexiaide.controller;

import com.lexiaide.common.dto.ProfessionalDocumentDto;
import com.lexiaide.service.ProfessionalDocumentService;
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
@RequestMapping("/api/v1/professional-documents")
public class ProfessionalDocumentController {

    private final ProfessionalDocumentService professionalDocumentService;

    public ProfessionalDocumentController(ProfessionalDocumentService professionalDocumentService) {
        this.professionalDocumentService = professionalDocumentService;
    }

    @PostMapping
    public ResponseEntity<ProfessionalDocumentDto.Response> create(@Valid @RequestBody ProfessionalDocumentDto.CreateRequest request) {
        ProfessionalDocumentDto.Response created = professionalDocumentService.create(request);
        return ResponseEntity.created(URI.create("/api/v1/professional-documents/" + created.id())).body(created);
    }

    @GetMapping("/{id}")
    public ProfessionalDocumentDto.Response findById(@PathVariable UUID id) {
        return professionalDocumentService.findById(id);
    }

    @GetMapping
    public Page<ProfessionalDocumentDto.Response> findAll(
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return professionalDocumentService.findAll(pageable);
    }

    @PutMapping("/{id}")
    public ProfessionalDocumentDto.Response update(@PathVariable UUID id, @Valid @RequestBody ProfessionalDocumentDto.UpdateRequest request) {
        return professionalDocumentService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        professionalDocumentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

