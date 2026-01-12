package com.example.PortfolioManager.repository;

import com.example.PortfolioManager.model.Saving;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingRepository extends JpaRepository<Saving, Long> {
}