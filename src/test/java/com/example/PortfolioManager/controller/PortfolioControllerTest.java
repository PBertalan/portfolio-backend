package com.example.PortfolioManager.controller;

import com.example.PortfolioManager.dto.PortfolioRequestDTO;
import com.example.PortfolioManager.model.Portfolio;
import com.example.PortfolioManager.service.impl.PortfolioServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PortfolioController.class)
@AutoConfigureMockMvc(addFilters = false)
class PortfolioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PortfolioServiceImpl portfolioService;

    @Test
    @DisplayName("POST /api/portfolio should return saved portfolio")
    void createPortfolio_shouldReturnSavedPortfolio() throws Exception {
        Portfolio saved = Portfolio.builder()
                .id(1L)
                .name("Family Fund")
                .description("Long-term savings")
                .build();

        when(portfolioService.createPortfolio(any(PortfolioRequestDTO.class))).thenReturn(saved);

        PortfolioRequestDTO request = new PortfolioRequestDTO();
        request.setName("Family Fund");
        request.setDescription("Long-term savings");

        mockMvc.perform(post("/api/portfolio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(saved.getId()))
                .andExpect(jsonPath("$.name").value(saved.getName()))
                .andExpect(jsonPath("$.description").value(saved.getDescription()));

        verify(portfolioService).createPortfolio(any(PortfolioRequestDTO.class));
    }

    @Test
    @DisplayName("POST /api/portfolio without name should return 400")
    void createPortfolio_missingName_shouldReturnBadRequest() throws Exception {
        String payload = """
                {
                  "description": "Missing name field"
                }
                """;

        mockMvc.perform(post("/api/portfolio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(portfolioService);
    }
}