package com.example.PortfolioManager.service;

import com.example.PortfolioManager.dto.InvestmentRequestDTO;
import com.example.PortfolioManager.model.Investment;
import com.example.PortfolioManager.model.Portfolio;
import com.example.PortfolioManager.repository.InvestmentRepository;
import com.example.PortfolioManager.repository.PortfolioRepository;
import com.example.PortfolioManager.service.impl.InvestmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InvestmentServiceImplTest {

    @Mock
    private InvestmentRepository investmentRepository;

    @Mock
    private PortfolioRepository portfolioRepository;

    @InjectMocks
    private InvestmentServiceImpl investmentService;

    private Portfolio portfolio;

    @BeforeEach
    void setUp() {
        portfolio = Portfolio.builder().id(12L).name("Invest PF").build();
    }

    @Test
    void createInvestment_shouldPersistWithPortfolio() {
        InvestmentRequestDTO dto = new InvestmentRequestDTO();
        dto.setAmount(new BigDecimal("2500.50"));
        dto.setDescription("ETF buy");
        dto.setAsset("ETF");
        dto.setPortfolioId(portfolio.getId());

        when(portfolioRepository.findById(portfolio.getId())).thenReturn(Optional.of(portfolio));
        Investment saved = Investment.builder()
                .id(3L)
                .amount(dto.getAmount())
                .asset(dto.getAsset())
                .portfolio(portfolio)
                .date(LocalDateTime.of(2024, 3, 10, 0, 0))
                .build();
        when(investmentRepository.save(org.mockito.ArgumentMatchers.any(Investment.class))).thenReturn(saved);

        Investment result = investmentService.createInvestment(dto);

        assertThat(result).isEqualTo(saved);

    }

    @Test
    void createInvestment_whenPortfolioMissing_shouldThrow() {
        InvestmentRequestDTO dto = new InvestmentRequestDTO();
        dto.setAmount(new BigDecimal("99.99"));
        dto.setAsset("Stock");
        dto.setPortfolioId(999L);

        when(portfolioRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(jakarta.persistence.EntityNotFoundException.class, () -> investmentService.createInvestment(dto));
    }
}