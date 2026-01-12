package com.example.PortfolioManager.repository;

import com.example.PortfolioManager.model.Investment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvestmentRepository extends JpaRepository<Investment, Long> {
}