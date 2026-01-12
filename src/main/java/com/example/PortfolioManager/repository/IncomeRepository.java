package com.example.PortfolioManager.repository;

import com.example.PortfolioManager.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeRepository extends JpaRepository<Income, Long> {
}