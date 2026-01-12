package com.example.PortfolioManager.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvestmentRequestDTO {

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal amount;

    private String description;

    @NotBlank
    private String asset;

    @NotNull
    private Long portfolioId;
}