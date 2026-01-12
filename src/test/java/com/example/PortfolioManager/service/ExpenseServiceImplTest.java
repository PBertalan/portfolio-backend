package com.example.PortfolioManager.service;

import com.example.PortfolioManager.dto.ExpenseRequestDTO;
import com.example.PortfolioManager.model.Expense;
import com.example.PortfolioManager.model.Portfolio;
import com.example.PortfolioManager.repository.ExpenseRepository;
import com.example.PortfolioManager.repository.PortfolioRepository;
import com.example.PortfolioManager.service.impl.ExpenseServiceImpl;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceImplTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private PortfolioRepository portfolioRepository;

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    private Portfolio portfolio;

    @BeforeEach
    void setUp() {
        portfolio = Portfolio.builder().id(10L).name("P1").build();
    }

    @Test
    void createExpense_shouldPersistWithPortfolio() {
        ExpenseRequestDTO dto = new ExpenseRequestDTO();
        dto.setAmount(new BigDecimal("12.34"));
        dto.setDescription("Coffee");
        dto.setType("Food");
        dto.setPortfolioId(portfolio.getId());

        when(portfolioRepository.findById(portfolio.getId())).thenReturn(Optional.of(portfolio));
        Expense saved = Expense.builder()
                .id(1L)
                .amount(dto.getAmount())
                .portfolio(portfolio)
                .date(LocalDateTime.of(2024, 1, 1, 0, 0))
                .build();
        when(expenseRepository.save(org.mockito.ArgumentMatchers.any(Expense.class))).thenReturn(saved);

        Expense result = expenseService.createExpense(dto);

        assertThat(result).isEqualTo(saved);

    }

    @Test
    void createExpense_whenPortfolioMissing_shouldThrow() {
        ExpenseRequestDTO dto = new ExpenseRequestDTO();
        dto.setAmount(new BigDecimal("1.00"));
        dto.setPortfolioId(999L);

        when(portfolioRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(jakarta.persistence.EntityNotFoundException.class, () -> expenseService.createExpense(dto));
    }
}