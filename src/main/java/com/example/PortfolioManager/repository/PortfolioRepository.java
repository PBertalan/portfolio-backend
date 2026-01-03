package com.example.PortfolioManager.repository;

import com.example.PortfolioManager.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
}