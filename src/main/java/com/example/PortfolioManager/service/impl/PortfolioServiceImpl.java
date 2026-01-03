package com.example.PortfolioManager.service.impl;

import com.example.PortfolioManager.dto.PortfolioRequestDTO;
import com.example.PortfolioManager.model.Portfolio;
import com.example.PortfolioManager.repository.PortfolioRepository;
import com.example.PortfolioManager.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioRepository portfolioRepository;

    public Portfolio createPortfolio(PortfolioRequestDTO dto) {
        final Portfolio portfolio = Portfolio.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
        return portfolioRepository.save(portfolio);
    }
}