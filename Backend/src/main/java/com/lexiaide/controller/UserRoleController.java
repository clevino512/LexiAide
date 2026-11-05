package com.lexiaide.controller;

import com.lexiaide.common.dto.UserRoleDto;
import com.lexiaide.model.UserRoleId;
import com.lexiaide.service.UserRoleService;
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
@RequestMapping("/api/v1/user-roles")
public class UserRoleController {

    private final UserRoleService userRoleService;

    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @PostMapping
    public ResponseEntity<UserRoleDto.Response> create(@Valid @RequestBody UserRoleDto.CreateRequest request) {
        UserRoleDto.Response created = userRoleService.create(request);
        return ResponseEntity.created(URI.create("/api/v1/user-roles/" + created.userId() + "/" + created.roleId())).body(created);
    }

    @GetMapping("/{userId}/{roleId}")
    public UserRoleDto.Response findById(@PathVariable UUID userId, @PathVariable UUID roleId) {
        return userRoleService.findById(new UserRoleId(userId, roleId));
    }

    @GetMapping
    public Page<UserRoleDto.Response> findAll(
            @PageableDefault(size = 20, sort = "id.userId", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return userRoleService.findAll(pageable);
    }

    @PutMapping("/{userId}/{roleId}")
    public UserRoleDto.Response update(@PathVariable UUID userId, @PathVariable UUID roleId, @Valid @RequestBody UserRoleDto.UpdateRequest request) {
        return userRoleService.update(new UserRoleId(userId, roleId), request);
    }

    @DeleteMapping("/{userId}/{roleId}")
    public ResponseEntity<Void> delete(@PathVariable UUID userId, @PathVariable UUID roleId) {
        userRoleService.delete(new UserRoleId(userId, roleId));
        return ResponseEntity.noContent().build();
    }
}

