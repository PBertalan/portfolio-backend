package com.example.PortfolioManager.controller;

import com.example.PortfolioManager.dto.InvestmentRequestDTO;
import com.example.PortfolioManager.model.Investment;
import com.example.PortfolioManager.service.InvestmentService;
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

@WebMvcTest(controllers = InvestmentController.class)
@AutoConfigureMockMvc(addFilters = false)
class InvestmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private InvestmentService investmentService;

    @Test
    @DisplayName("POST /api/investment should return saved investment")
    void createInvestment_shouldReturnSavedInvestment() throws Exception {
        Investment saved = Investment.builder()
                .id(3L)
                .amount(new BigDecimal("2500.50"))
                .description("ETF buy")
                .asset("ETF")
                .date(LocalDateTime.of(2024, 3, 10, 0, 0))
                .build();

        when(investmentService.createInvestment(any(InvestmentRequestDTO.class))).thenReturn(saved);

        InvestmentRequestDTO request = new InvestmentRequestDTO();
        request.setAmount(new BigDecimal("2500.50"));
        request.setDescription("ETF buy");
        request.setAsset("ETF");
        request.setPortfolioId(12L);

        mockMvc.perform(post("/api/investment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(saved.getId()))
                .andExpect(jsonPath("$.amount").value(saved.getAmount().doubleValue()))
                .andExpect(jsonPath("$.description").value(saved.getDescription()))
                .andExpect(jsonPath("$.asset").value(saved.getAsset()));

        verify(investmentService).createInvestment(any(InvestmentRequestDTO.class));
    }

    @Test
    @DisplayName("POST /api/investment without amount should return 400")
    void createInvestment_missingRequired_shouldReturnBadRequest() throws Exception {
        String payload = """
                {
                  "asset": "Stock",
                  "portfolioId": 5
                }
                """;

        mockMvc.perform(post("/api/investment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(investmentService);
    }
}