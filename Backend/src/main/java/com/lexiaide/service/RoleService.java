package com.lexiaide.service;

import com.lexiaide.common.dto.RoleDto;
import com.lexiaide.common.exception.ResourceNotFoundException;
import com.lexiaide.model.Role;
import com.lexiaide.repository.RoleRepository;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional
    public RoleDto.Response create(RoleDto.CreateRequest request) {
        Role role = new Role();
        role.setCode(request.code());
        role.setName(request.name());
        return toResponse(roleRepository.save(role));
    }

    @Transactional(readOnly = true)
    public RoleDto.Response findById(UUID id) {
        return toResponse(getRole(id));
    }

    @Transactional(readOnly = true)
    public Page<RoleDto.Response> findAll(Pageable pageable) {
        return roleRepository.findAll(pageable).map(RoleService::toResponse);
    }

    @Transactional
    public RoleDto.Response update(UUID id, RoleDto.UpdateRequest request) {
        Role existing = getRole(id);
        existing.setCode(request.code());
        existing.setName(request.name());
        return toResponse(roleRepository.save(existing));
    }

    @Transactional
    public void delete(UUID id) {
        roleRepository.delete(getRole(id));
    }

    private Role getRole(UUID id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role introuvable : " + id));
    }

    private static RoleDto.Response toResponse(Role role) {
        return new RoleDto.Response(role.getId(), role.getCode(), role.getName());
    }
}

