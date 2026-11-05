package com.lexiaide.controller;

import com.lexiaide.common.dto.RoleDto;
import com.lexiaide.service.RoleService;
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
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<RoleDto.Response> create(@Valid @RequestBody RoleDto.CreateRequest request) {
        RoleDto.Response created = roleService.create(request);
        return ResponseEntity.created(URI.create("/api/v1/roles/" + created.id())).body(created);
    }

    @GetMapping("/{id}")
    public RoleDto.Response findById(@PathVariable UUID id) {
        return roleService.findById(id);
    }

    @GetMapping
    public Page<RoleDto.Response> findAll(
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return roleService.findAll(pageable);
    }

    @PutMapping("/{id}")
    public RoleDto.Response update(@PathVariable UUID id, @Valid @RequestBody RoleDto.UpdateRequest request) {
        return roleService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

