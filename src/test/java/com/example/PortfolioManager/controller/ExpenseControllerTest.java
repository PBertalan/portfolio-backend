package com.example.PortfolioManager.controller;

import com.example.PortfolioManager.dto.ExpenseRequestDTO;
import com.example.PortfolioManager.model.Expense;
import com.example.PortfolioManager.service.ExpenseService;
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

@WebMvcTest(controllers = ExpenseController.class)
@AutoConfigureMockMvc(addFilters = false)
class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ExpenseService expenseService;

    @Test
    @DisplayName("POST /api/expense should return saved expense")
    void createExpense_shouldReturnSavedExpense() throws Exception {
        Expense saved = Expense.builder()
                .id(1L)
                .amount(new BigDecimal("12.34"))
                .description("Coffee")
                .type("Food")
                .date(LocalDateTime.of(2024, 1, 1, 0, 0))
                .build();

        when(expenseService.createExpense(any(ExpenseRequestDTO.class))).thenReturn(saved);

        ExpenseRequestDTO request = new ExpenseRequestDTO();
        request.setAmount(new BigDecimal("12.34"));
        request.setDescription("Coffee");
        request.setType("Food");
        request.setPortfolioId(10L);

        mockMvc.perform(post("/api/expense")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(saved.getId()))
                .andExpect(jsonPath("$.amount").value(saved.getAmount()))
                .andExpect(jsonPath("$.description").value(saved.getDescription()))
                .andExpect(jsonPath("$.type").value(saved.getType()));

        verify(expenseService).createExpense(any(ExpenseRequestDTO.class));
    }

    @Test
    @DisplayName("POST /api/expense without required fields should return 400")
    void createExpense_missingRequired_shouldReturnBadRequest() throws Exception {
        String payload = """
                {
                  "description": "no amount",
                  "portfolioId": 5
                }
                """;

        mockMvc.perform(post("/api/expense")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(expenseService);
    }
}