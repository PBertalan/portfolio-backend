package com.example.PortfolioManager.service;

import com.example.PortfolioManager.dto.IncomeRequestDTO;
import com.example.PortfolioManager.model.Income;

/**
 * Bevételen elvégezhető műveleteket gyüjtő service.
 */
public interface IncomeService {

    Income createIncome(IncomeRequestDTO dto);
}