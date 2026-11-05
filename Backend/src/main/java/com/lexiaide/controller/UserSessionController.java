package com.lexiaide.controller;

import com.lexiaide.common.dto.UserSessionDto;
import com.lexiaide.service.UserSessionService;
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
@RequestMapping("/api/v1/user-sessions")
public class UserSessionController {

    private final UserSessionService userSessionService;

    public UserSessionController(UserSessionService userSessionService) {
        this.userSessionService = userSessionService;
    }

    @PostMapping
    public ResponseEntity<UserSessionDto.Response> create(@Valid @RequestBody UserSessionDto.CreateRequest request) {
        UserSessionDto.Response created = userSessionService.create(request);
        return ResponseEntity.created(URI.create("/api/v1/user-sessions/" + created.id())).body(created);
    }

    @GetMapping("/{id}")
    public UserSessionDto.Response findById(@PathVariable UUID id) {
        return userSessionService.findById(id);
    }

    @GetMapping
    public Page<UserSessionDto.Response> findAll(
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return userSessionService.findAll(pageable);
    }

    @PutMapping("/{id}")
    public UserSessionDto.Response update(@PathVariable UUID id, @Valid @RequestBody UserSessionDto.UpdateRequest request) {
        return userSessionService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        userSessionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

