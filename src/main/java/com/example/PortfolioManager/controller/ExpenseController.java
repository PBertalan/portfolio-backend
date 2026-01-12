package com.example.PortfolioManager.controller;

import com.example.PortfolioManager.dto.ExpenseRequestDTO;
import com.example.PortfolioManager.model.Expense;
import com.example.PortfolioManager.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/expense")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<Expense> create(@RequestBody @Valid ExpenseRequestDTO request) {
        return ResponseEntity.ok(expenseService.createExpense(request));
    }
}