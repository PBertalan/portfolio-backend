package com.example.PortfolioManager.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PortfolioRequestDTO {

    @NotBlank
    private String name;
    private String description;
}