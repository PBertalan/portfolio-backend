package com.example.PortfolioManager.controller;

import com.example.PortfolioManager.dto.IncomeRequestDTO;
import com.example.PortfolioManager.model.Income;
import com.example.PortfolioManager.service.IncomeService;
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

@WebMvcTest(controllers = IncomeController.class)
@AutoConfigureMockMvc(addFilters = false)
class IncomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private IncomeService incomeService;

    @Test
    @DisplayName("POST /api/income should return saved income")
    void createIncome_shouldReturnSavedIncome() throws Exception {
        Income saved = Income.builder()
                .id(2L)
                .amount(new BigDecimal("1500.00"))
                .description("Salary")
                .type("Job")
                .date(LocalDateTime.of(2024, 2, 1, 0, 0))
                .build();

        when(incomeService.createIncome(any(IncomeRequestDTO.class))).thenReturn(saved);

        IncomeRequestDTO request = new IncomeRequestDTO();
        request.setAmount(new BigDecimal("1500.00"));
        request.setDescription("Salary");
        request.setType("Job");
        request.setPortfolioId(11L);

        mockMvc.perform(post("/api/income")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(saved.getId()))
                .andExpect(jsonPath("$.amount").value(saved.getAmount().doubleValue()))
                .andExpect(jsonPath("$.description").value(saved.getDescription()))
                .andExpect(jsonPath("$.type").value(saved.getType()));

        verify(incomeService).createIncome(any(IncomeRequestDTO.class));
    }

    @Test
    @DisplayName("POST /api/income without amount should return 400")
    void createIncome_missingRequired_shouldReturnBadRequest() throws Exception {
        String payload = """
                {
                  "description": "no amount",
                  "portfolioId": 5
                }
                """;

        mockMvc.perform(post("/api/income")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(incomeService);
    }
}