package com.lexiaide.service;

import com.lexiaide.common.dto.UserDto;
import com.lexiaide.common.exception.ResourceNotFoundException;
import com.lexiaide.model.User;
import com.lexiaide.model.UserStatus;
import com.lexiaide.repository.UserRepository;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDto.Response create(UserDto.CreateRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Un utilisateur avec cet email existe deja.");
        }
        if (userRepository.existsByPhone(request.phone())) {
            throw new IllegalArgumentException("Un utilisateur avec ce numero de telephone existe deja.");
        }

        User user = new User();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPhone(request.phone());
        user.setPasswordHash(request.passwordHash());
        user.setPhotoUrl(request.photoUrl());
        user.setPreferredLanguage(request.preferredLanguage());
        user.setStatus(UserStatus.PENDING);
        user.setCreatedAt(Instant.now());

        return toResponse(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public UserDto.Response findById(UUID id) {
        return toResponse(getUser(id));
    }

    @Transactional(readOnly = true)
    public Page<UserDto.Response> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserService::toResponse);
    }

    @Transactional
    public UserDto.Response update(UUID id, UserDto.UpdateRequest request) {
        User user = getUser(id);

        if (!Objects.equals(user.getEmail(), request.email()) && userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Un utilisateur avec cet email existe deja.");
        }
        if (!Objects.equals(user.getPhone(), request.phone()) && userRepository.existsByPhone(request.phone())) {
            throw new IllegalArgumentException("Un utilisateur avec ce numero de telephone existe deja.");
        }

        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPhone(request.phone());
        user.setPhotoUrl(request.photoUrl());
        user.setPreferredLanguage(request.preferredLanguage());
        if (request.status() != null) {
            user.setStatus(request.status());
        }

        return toResponse(userRepository.save(user));
    }

    @Transactional
    public void delete(UUID id) {
        userRepository.delete(getUser(id));
    }

    private User getUser(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur introuvable : " + id));
    }

    private static UserDto.Response toResponse(User user) {
        return new UserDto.Response(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getPhotoUrl(),
                user.getPreferredLanguage(),
                user.getStatus(),
                user.getEmailVerifiedAt(),
                user.getCreatedAt()
        );
    }
}