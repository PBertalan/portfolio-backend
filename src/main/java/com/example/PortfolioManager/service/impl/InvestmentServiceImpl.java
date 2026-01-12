package com.example.PortfolioManager.service.impl;

import com.example.PortfolioManager.dto.InvestmentRequestDTO;
import com.example.PortfolioManager.model.Investment;
import com.example.PortfolioManager.model.Portfolio;
import com.example.PortfolioManager.repository.InvestmentRepository;
import com.example.PortfolioManager.repository.PortfolioRepository;
import com.example.PortfolioManager.service.InvestmentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvestmentServiceImpl implements InvestmentService {

    private final InvestmentRepository investmentRepository;
    private final PortfolioRepository portfolioRepository;

    @Override
    public Investment createInvestment(InvestmentRequestDTO dto) {
        Portfolio portfolio = portfolioRepository.findById(dto.getPortfolioId())
                .orElseThrow(() -> new EntityNotFoundException("Portfolio not found: " + dto.getPortfolioId()));

        Investment investment = buildInvestment(dto, portfolio);

        return investmentRepository.save(investment);
    }

    private Investment buildInvestment(InvestmentRequestDTO dto, Portfolio portfolio) {
        return Investment.builder()
                .amount(dto.getAmount())
                .description(dto.getDescription())
                .asset(dto.getAsset())
                .portfolio(portfolio)
                .build();
    }
}