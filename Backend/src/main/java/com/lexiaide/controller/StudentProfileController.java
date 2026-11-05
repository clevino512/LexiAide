package com.lexiaide.controller;

import com.lexiaide.common.dto.StudentProfileDto;
import com.lexiaide.service.StudentProfileService;
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
@RequestMapping("/api/v1/student-profiles")
public class StudentProfileController {

    private final StudentProfileService studentProfileService;

    public StudentProfileController(StudentProfileService studentProfileService) {
        this.studentProfileService = studentProfileService;
    }

    @PostMapping
    public ResponseEntity<StudentProfileDto.Response> create(@Valid @RequestBody StudentProfileDto.CreateRequest request) {
        StudentProfileDto.Response created = studentProfileService.create(request);
        return ResponseEntity.created(URI.create("/api/v1/student-profiles/" + created.id())).body(created);
    }

    @GetMapping("/{id}")
    public StudentProfileDto.Response findById(@PathVariable UUID id) {
        return studentProfileService.findById(id);
    }

    @GetMapping
    public Page<StudentProfileDto.Response> findAll(
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return studentProfileService.findAll(pageable);
    }

    @PutMapping("/{id}")
    public StudentProfileDto.Response update(@PathVariable UUID id, @Valid @RequestBody StudentProfileDto.UpdateRequest request) {
        return studentProfileService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        studentProfileService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

