package com.lexiaide.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.lexiaide.common.dto.HealthResponse;
import com.lexiaide.service.HealthService;
import java.time.Instant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(HealthController.class)
class HealthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private HealthService healthService;

    @Test
    @DisplayName("GET /api/v1/health returns status UP")
    void shouldReturnHealthStatusUp() throws Exception {
        when(healthService.getHealth())
                .thenReturn(new HealthResponse("UP", Instant.parse("2026-10-18T00:00:00Z")));

        mockMvc.perform(get("/api/v1/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.timestamp").value("2026-10-18T00:00:00Z"));
    }

    @Test
    @DisplayName("GET /api/v1/health returns correct Content-Type")
    void shouldReturnJsonContentType() throws Exception {
        when(healthService.getHealth())
                .thenReturn(new HealthResponse("UP", Instant.parse("2026-10-18T00:00:00Z")));

        mockMvc.perform(get("/api/v1/health"))
                .andExpect(status().isOk())
                .andExpect(status().is(200));
    }
}
