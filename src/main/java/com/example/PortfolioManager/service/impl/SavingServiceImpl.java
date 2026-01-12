package com.example.PortfolioManager.service.impl;

import com.example.PortfolioManager.dto.SavingRequestDTO;
import com.example.PortfolioManager.model.Portfolio;
import com.example.PortfolioManager.model.Saving;
import com.example.PortfolioManager.repository.PortfolioRepository;
import com.example.PortfolioManager.repository.SavingRepository;
import com.example.PortfolioManager.service.SavingService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SavingServiceImpl implements SavingService {

    private final SavingRepository savingRepository;
    private final PortfolioRepository portfolioRepository;

    @Override
    public Saving createSaving(SavingRequestDTO dto) {
        Portfolio portfolio = portfolioRepository.findById(dto.getPortfolioId())
                .orElseThrow(() -> new EntityNotFoundException("Portfolio not found: " + dto.getPortfolioId()));

        Saving saving = buildSaving(dto, portfolio);

        return savingRepository.save(saving);
    }

    private Saving buildSaving(SavingRequestDTO dto, Portfolio portfolio) {
        return Saving.builder()
                .amount(dto.getAmount())
                .description(dto.getDescription())
                .type(dto.getType())
                .portfolio(portfolio)
                .build();
    }
}