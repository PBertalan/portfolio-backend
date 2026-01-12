package com.example.PortfolioManager.service;

import com.example.PortfolioManager.dto.ExpenseRequestDTO;
import com.example.PortfolioManager.model.Expense;

/**
 * Kiadásokon elvégezhető műveleteket gyüjtő service.
 */
public interface ExpenseService {

    Expense createExpense(ExpenseRequestDTO dto);
}