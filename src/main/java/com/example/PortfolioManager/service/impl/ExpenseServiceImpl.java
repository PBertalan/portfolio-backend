package com.example.PortfolioManager.service.impl;

import com.example.PortfolioManager.dto.ExpenseRequestDTO;
import com.example.PortfolioManager.model.Expense;
import com.example.PortfolioManager.model.Portfolio;
import com.example.PortfolioManager.repository.ExpenseRepository;
import com.example.PortfolioManager.repository.PortfolioRepository;
import com.example.PortfolioManager.service.ExpenseService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final PortfolioRepository portfolioRepository;

    @Override
    public Expense createExpense(ExpenseRequestDTO dto) {
        Portfolio portfolio = portfolioRepository.findById(dto.getPortfolioId())
                .orElseThrow(() -> new EntityNotFoundException("Portfolio not found: " + dto.getPortfolioId()));

        Expense expense = buildExpense(dto, portfolio);

        return expenseRepository.save(expense);
    }

    private Expense buildExpense(ExpenseRequestDTO dto, Portfolio portfolio) {
        return Expense.builder()
                .amount(dto.getAmount())
                .description(dto.getDescription())
                .type(dto.getType())
                .portfolio(portfolio)
                .build();
    }
}