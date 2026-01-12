package com.example.PortfolioManager.controller;

import com.example.PortfolioManager.dto.IncomeRequestDTO;
import com.example.PortfolioManager.model.Income;
import com.example.PortfolioManager.service.IncomeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/income")
@RequiredArgsConstructor
public class IncomeController {

    private final IncomeService incomeService;

    @PostMapping
    public ResponseEntity<Income> create(@RequestBody @Valid IncomeRequestDTO request) {
        return ResponseEntity.ok(incomeService.createIncome(request));
    }
}