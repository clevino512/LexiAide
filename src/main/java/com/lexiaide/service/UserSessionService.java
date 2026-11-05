package com.lexiaide.service;

import com.lexiaide.common.dto.UserSessionDto;
import com.lexiaide.common.exception.ResourceNotFoundException;
import com.lexiaide.model.User;
import com.lexiaide.model.UserSession;
import com.lexiaide.repository.UserRepository;
import com.lexiaide.repository.UserSessionRepository;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserSessionService {

    private final UserSessionRepository userSessionRepository;
    private final UserRepository userRepository;

    public UserSessionService(UserSessionRepository userSessionRepository, UserRepository userRepository) {
        this.userSessionRepository = userSessionRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public UserSessionDto.Response create(UserSessionDto.CreateRequest request) {
        UserSession userSession = new UserSession();
        userSession.setUser(getUser(request.userId()));
        userSession.setRefreshTokenHash(request.refreshTokenHash());
        userSession.setDeviceId(request.deviceId());
        userSession.setIpAddress(request.ipAddress());
        userSession.setExpiresAt(request.expiresAt());
        return toResponse(userSessionRepository.save(userSession));
    }

    @Transactional(readOnly = true)
    public UserSessionDto.Response findById(UUID id) {
        return toResponse(getUserSession(id));
    }

    @Transactional(readOnly = true)
    public Page<UserSessionDto.Response> findAll(Pageable pageable) {
        return userSessionRepository.findAll(pageable).map(UserSessionService::toResponse);
    }

    @Transactional
    public UserSessionDto.Response update(UUID id, UserSessionDto.UpdateRequest request) {
        UserSession existing = getUserSession(id);
        existing.setDeviceId(request.deviceId());
        existing.setIpAddress(request.ipAddress());
        existing.setExpiresAt(request.expiresAt());
        existing.setRevokedAt(request.revokedAt());
        return toResponse(userSessionRepository.save(existing));
    }

    @Transactional
    public void delete(UUID id) {
        userSessionRepository.delete(getUserSession(id));
    }

    private UserSession getUserSession(UUID id) {
        return userSessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Session utilisateur introuvable : " + id));
    }

    private User getUser(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur introuvable : " + id));
    }

    private static UserSessionDto.Response toResponse(UserSession userSession) {
        return new UserSessionDto.Response(
                userSession.getId(),
                userSession.getUser() != null ? userSession.getUser().getId() : null,
                userSession.getDeviceId(),
                userSession.getIpAddress(),
                userSession.getExpiresAt(),
                userSession.getRevokedAt()
        );
    }
}

