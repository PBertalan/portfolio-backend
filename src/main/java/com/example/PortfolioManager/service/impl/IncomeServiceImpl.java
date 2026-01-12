package com.example.PortfolioManager.service.impl;

import com.example.PortfolioManager.dto.IncomeRequestDTO;
import com.example.PortfolioManager.model.Income;
import com.example.PortfolioManager.model.Portfolio;
import com.example.PortfolioManager.repository.IncomeRepository;
import com.example.PortfolioManager.repository.PortfolioRepository;
import com.example.PortfolioManager.service.IncomeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository incomeRepository;
    private final PortfolioRepository portfolioRepository;

    @Override
    public Income createIncome(IncomeRequestDTO dto) {
        Portfolio portfolio = portfolioRepository.findById(dto.getPortfolioId())
                .orElseThrow(() -> new EntityNotFoundException("Portfolio not found: " + dto.getPortfolioId()));

        Income income = buildIncome(dto, portfolio);

        return incomeRepository.save(income);
    }

    private Income buildIncome(IncomeRequestDTO dto, Portfolio portfolio) {
        return Income.builder()
                .amount(dto.getAmount())
                .description(dto.getDescription())
                .type(dto.getType())
                .portfolio(portfolio)
                .build();
    }
}