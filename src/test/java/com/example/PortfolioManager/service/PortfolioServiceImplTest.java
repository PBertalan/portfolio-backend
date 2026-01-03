package com.example.PortfolioManager.service;

import com.example.PortfolioManager.dto.PortfolioRequestDTO;
import com.example.PortfolioManager.model.Portfolio;
import com.example.PortfolioManager.repository.PortfolioRepository;
import com.example.PortfolioManager.service.impl.PortfolioServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PortfolioServiceImplTest {

    @Mock
    private PortfolioRepository portfolioRepository;

    @InjectMocks
    private PortfolioServiceImpl portfolioService;

    @Test
    void createPortfolio_shouldPersistAndReturnSavedEntity() {
        // Arrange
        PortfolioRequestDTO request = new PortfolioRequestDTO();
        request.setName("Family Fund");
        request.setDescription("Long-term savings");

        Portfolio saved = Portfolio.builder()
                .id(1L)
                .name(request.getName())
                .description(request.getDescription())
                .build();

        when(portfolioRepository.save(org.mockito.ArgumentMatchers.any(Portfolio.class)))
                .thenReturn(saved);

        // Act
        Portfolio result = portfolioService.createPortfolio(request);

        // Assert: returned value is what repository returned
        assertThat(result).isEqualTo(saved);

    }
}