package com.example.PortfolioManager.service;

import com.example.PortfolioManager.dto.PortfolioRequestDTO;
import com.example.PortfolioManager.model.Portfolio;

/**
 * Portfolión elvégezhető műveleteket gyüjtő service.
 */
public interface PortfolioService {

    Portfolio createPortfolio(PortfolioRequestDTO dto);

}
