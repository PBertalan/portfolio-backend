package com.example.PortfolioManager.service;

import com.example.PortfolioManager.dto.SavingRequestDTO;
import com.example.PortfolioManager.model.Saving;

/**
 * Megtakarításon elvégezhető műveleteket gyüjtő service.
 */
public interface SavingService {

    Saving createSaving(SavingRequestDTO dto);
}