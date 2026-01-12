package com.example.PortfolioManager.controller;

import com.example.PortfolioManager.dto.SavingRequestDTO;
import com.example.PortfolioManager.model.Saving;
import com.example.PortfolioManager.service.SavingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SavingController.class)
@AutoConfigureMockMvc(addFilters = false)
class SavingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private SavingService savingService;

    @Test
    @DisplayName("POST /api/saving should return saved saving")
    void createSaving_shouldReturnSavedSaving() throws Exception {
        Saving saved = Saving.builder()
                .id(4L)
                .amount(new BigDecimal("500.00"))
                .description("Emergency fund")
                .type("Cash")
                .date(LocalDateTime.of(2024, 4, 5, 0, 0))
                .build();

        when(savingService.createSaving(any(SavingRequestDTO.class))).thenReturn(saved);

        SavingRequestDTO request = new SavingRequestDTO();
        request.setAmount(new BigDecimal("500.00"));
        request.setDescription("Emergency fund");
        request.setType("Cash");
        request.setPortfolioId(13L);

        mockMvc.perform(post("/api/saving")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(saved.getId()))
                .andExpect(jsonPath("$.amount").value(saved.getAmount().doubleValue()))
                .andExpect(jsonPath("$.description").value(saved.getDescription()))
                .andExpect(jsonPath("$.type").value(saved.getType()));

        verify(savingService).createSaving(any(SavingRequestDTO.class));
    }

    @Test
    @DisplayName("POST /api/saving without amount should return 400")
    void createSaving_missingRequired_shouldReturnBadRequest() throws Exception {
        String payload = """
                {
                  "description": "no amount",
                  "portfolioId": 5
                }
                """;

        mockMvc.perform(post("/api/saving")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(savingService);
    }
}