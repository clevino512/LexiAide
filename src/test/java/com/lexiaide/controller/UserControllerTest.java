package com.lexiaide.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lexiaide.common.dto.UserDto;
import com.lexiaide.common.exception.ResourceNotFoundException;
import com.lexiaide.model.PreferredLanguage;
import com.lexiaide.model.UserStatus;
import com.lexiaide.service.UserService;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
class UserControllerTest {

    private static final UUID USER_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final Instant CREATED_AT = Instant.parse("2026-01-01T00:00:00Z");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @Test
    @DisplayName("POST /api/v1/users returns 201 with the created user")
    void shouldCreateUser() throws Exception {
        UserDto.CreateRequest request = new UserDto.CreateRequest(
                "Rakoto", "Jean", "jean.rakoto@example.com", "+261340000000", "hashed", null, PreferredLanguage.FR);
        when(userService.create(any(UserDto.CreateRequest.class)))
                .thenReturn(userResponse(UserStatus.PENDING, PreferredLanguage.FR));

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/users/" + USER_ID))
                .andExpect(jsonPath("$.id").value(USER_ID.toString()))
                .andExpect(jsonPath("$.email").value("jean.rakoto@example.com"))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    @DisplayName("POST /api/v1/users returns 400 when the payload is invalid")
    void shouldRejectInvalidCreateRequest() throws Exception {
        UserDto.CreateRequest request = new UserDto.CreateRequest(
                "Rakoto", "Jean", "pas-un-email", "+261340000000", "hashed", null, PreferredLanguage.FR);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/v1/users/{id} returns the user")
    void shouldReturnUserById() throws Exception {
        when(userService.findById(USER_ID)).thenReturn(userResponse(UserStatus.ACTIVE, PreferredLanguage.FR));

        mockMvc.perform(get("/api/v1/users/{id}", USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(USER_ID.toString()))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    @DisplayName("GET /api/v1/users/{id} returns 404 when the user does not exist")
    void shouldReturnNotFoundWhenUserMissing() throws Exception {
        when(userService.findById(USER_ID))
                .thenThrow(new ResourceNotFoundException("Utilisateur introuvable : " + USER_ID));

        mockMvc.perform(get("/api/v1/users/{id}", USER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Utilisateur introuvable : " + USER_ID));
    }

    @Test
    @DisplayName("GET /api/v1/users returns a page of users")
    void shouldReturnPaginatedUsers() throws Exception {
        Pageable pageable = PageRequest.of(0, 20);
        when(userService.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(userResponse(UserStatus.ACTIVE, PreferredLanguage.FR)), pageable, 1));

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(USER_ID.toString()))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    @DisplayName("PUT /api/v1/users/{id} returns the updated user")
    void shouldUpdateUser() throws Exception {
        UserDto.UpdateRequest request = new UserDto.UpdateRequest(
                "Rakoto", "Jean", "jean.rakoto@example.com", "+261340000000", null, PreferredLanguage.MG, UserStatus.SUSPENDED);
        when(userService.update(eq(USER_ID), any(UserDto.UpdateRequest.class)))
                .thenReturn(userResponse(UserStatus.SUSPENDED, PreferredLanguage.MG));

        mockMvc.perform(put("/api/v1/users/{id}", USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUSPENDED"))
                .andExpect(jsonPath("$.preferredLanguage").value("MG"));
    }

    @Test
    @DisplayName("DELETE /api/v1/users/{id} returns 204")
    void shouldDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/v1/users/{id}", USER_ID))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /api/v1/users/{id} returns 404 when the user does not exist")
    void shouldReturnNotFoundOnDelete() throws Exception {
        doThrow(new ResourceNotFoundException("Utilisateur introuvable : " + USER_ID))
                .when(userService).delete(USER_ID);

        mockMvc.perform(delete("/api/v1/users/{id}", USER_ID))
                .andExpect(status().isNotFound());
    }

    private static UserDto.Response userResponse(UserStatus status, PreferredLanguage language) {
        return new UserDto.Response(
                USER_ID,
                "Rakoto",
                "Jean",
                "jean.rakoto@example.com",
                "+261340000000",
                null,
                language,
                status,
                null,
                CREATED_AT
        );
    }
}