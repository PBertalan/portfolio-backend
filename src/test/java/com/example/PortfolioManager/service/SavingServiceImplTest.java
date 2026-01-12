package com.example.PortfolioManager.service;

import com.example.PortfolioManager.dto.SavingRequestDTO;
import com.example.PortfolioManager.model.Portfolio;
import com.example.PortfolioManager.model.Saving;
import com.example.PortfolioManager.repository.PortfolioRepository;
import com.example.PortfolioManager.repository.SavingRepository;
import com.example.PortfolioManager.service.impl.SavingServiceImpl;
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
class SavingServiceImplTest {

    @Mock
    private SavingRepository savingRepository;

    @Mock
    private PortfolioRepository portfolioRepository;

    @InjectMocks
    private SavingServiceImpl savingService;

    private Portfolio portfolio;

    @BeforeEach
    void setUp() {
        portfolio = Portfolio.builder().id(13L).name("Save PF").build();
    }

    @Test
    void createSaving_shouldPersistWithPortfolio() {
        SavingRequestDTO dto = new SavingRequestDTO();
        dto.setAmount(new BigDecimal("500.00"));
        dto.setDescription("Emergency fund");
        dto.setType("Cash");
        dto.setPortfolioId(portfolio.getId());

        when(portfolioRepository.findById(portfolio.getId())).thenReturn(Optional.of(portfolio));
        Saving saved = Saving.builder()
                .id(4L)
                .amount(dto.getAmount())
                .portfolio(portfolio)
                .date(LocalDateTime.of(2024, 4, 5, 0, 0))
                .build();
        when(savingRepository.save(org.mockito.ArgumentMatchers.any(Saving.class))).thenReturn(saved);

        Saving result = savingService.createSaving(dto);

        assertThat(result).isEqualTo(saved);

    }

    @Test
    void createSaving_whenPortfolioMissing_shouldThrow() {
        SavingRequestDTO dto = new SavingRequestDTO();
        dto.setAmount(new BigDecimal("42.00"));
        dto.setPortfolioId(999L);

        when(portfolioRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(jakarta.persistence.EntityNotFoundException.class, () -> savingService.createSaving(dto));
    }
}