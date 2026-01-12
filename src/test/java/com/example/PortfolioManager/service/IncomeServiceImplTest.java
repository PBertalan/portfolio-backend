package com.example.PortfolioManager.service;

import com.example.PortfolioManager.dto.IncomeRequestDTO;
import com.example.PortfolioManager.model.Income;
import com.example.PortfolioManager.model.Portfolio;
import com.example.PortfolioManager.repository.IncomeRepository;
import com.example.PortfolioManager.repository.PortfolioRepository;
import com.example.PortfolioManager.service.impl.IncomeServiceImpl;
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
class IncomeServiceImplTest {

    @Mock
    private IncomeRepository incomeRepository;

    @Mock
    private PortfolioRepository portfolioRepository;

    @InjectMocks
    private IncomeServiceImpl incomeService;

    private Portfolio portfolio;

    @BeforeEach
    void setUp() {
        portfolio = Portfolio.builder().id(11L).name("Income PF").build();
    }

    @Test
    void createIncome_shouldPersistWithPortfolio() {
        IncomeRequestDTO dto = new IncomeRequestDTO();
        dto.setAmount(new BigDecimal("1500.00"));
        dto.setDescription("Salary");
        dto.setType("Job");
        dto.setPortfolioId(portfolio.getId());

        when(portfolioRepository.findById(portfolio.getId())).thenReturn(Optional.of(portfolio));
        Income saved = Income.builder()
                .id(2L)
                .amount(dto.getAmount())
                .portfolio(portfolio)
                .date(LocalDateTime.of(2024, 2, 1, 0, 0))
                .build();
        when(incomeRepository.save(org.mockito.ArgumentMatchers.any(Income.class))).thenReturn(saved);

        Income result = incomeService.createIncome(dto);

        assertThat(result).isEqualTo(saved);

    }

    @Test
    void createIncome_whenPortfolioMissing_shouldThrow() {
        IncomeRequestDTO dto = new IncomeRequestDTO();
        dto.setAmount(new BigDecimal("10.00"));
        dto.setPortfolioId(999L);

        when(portfolioRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(jakarta.persistence.EntityNotFoundException.class, () -> incomeService.createIncome(dto));
    }
}