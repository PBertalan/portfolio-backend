package com.example.PortfolioManager.service;

import com.example.PortfolioManager.dto.InvestmentRequestDTO;
import com.example.PortfolioManager.model.Investment;

/**
 * Befektetésen elvégezhető műveleteket gyüjtő service.
 */
public interface InvestmentService {

    Investment createInvestment(InvestmentRequestDTO dto);
}