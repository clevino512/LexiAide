package com.lexiaide.service;

import com.lexiaide.common.dto.UserRoleDto;
import com.lexiaide.common.exception.ResourceNotFoundException;
import com.lexiaide.model.Role;
import com.lexiaide.model.User;
import com.lexiaide.model.UserRole;
import com.lexiaide.model.UserRoleId;
import com.lexiaide.repository.RoleRepository;
import com.lexiaide.repository.UserRepository;
import com.lexiaide.repository.UserRoleRepository;
import java.time.Instant;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository, UserRepository userRepository, RoleRepository roleRepository) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public UserRoleDto.Response create(UserRoleDto.CreateRequest request) {
        UserRole userRole = new UserRole();
        userRole.setUser(getUser(request.userId()));
        userRole.setRole(getRole(request.roleId()));
        userRole.setAssignedAt(request.assignedAt() != null ? request.assignedAt() : Instant.now());
        return toResponse(userRoleRepository.save(userRole));
    }

    @Transactional(readOnly = true)
    public UserRoleDto.Response findById(UserRoleId id) {
        return toResponse(getUserRole(id));
    }

    @Transactional(readOnly = true)
    public Page<UserRoleDto.Response> findAll(Pageable pageable) {
        return userRoleRepository.findAll(pageable).map(UserRoleService::toResponse);
    }

    @Transactional
    public UserRoleDto.Response update(UserRoleId id, UserRoleDto.UpdateRequest request) {
        UserRole existing = getUserRole(id);
        existing.setAssignedAt(request.assignedAt());
        return toResponse(userRoleRepository.save(existing));
    }

    @Transactional
    public void delete(UserRoleId id) {
        userRoleRepository.delete(getUserRole(id));
    }

    private UserRole getUserRole(UserRoleId id) {
        return userRoleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role utilisateur introuvable : " + id));
    }

    private User getUser(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur introuvable : " + id));
    }

    private Role getRole(UUID id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role introuvable : " + id));
    }

    private static UserRoleDto.Response toResponse(UserRole userRole) {
        return new UserRoleDto.Response(userRole.getId().getUserId(), userRole.getId().getRoleId(), userRole.getAssignedAt());
    }
}

