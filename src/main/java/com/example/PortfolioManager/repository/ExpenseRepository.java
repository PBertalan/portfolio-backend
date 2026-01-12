package com.example.PortfolioManager.repository;

import com.example.PortfolioManager.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}