package com.example.PortfolioManager.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExpenseRequestDTO {

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal amount;

    private String description;

    private String type;

    @NotNull
    private Long portfolioId;
}