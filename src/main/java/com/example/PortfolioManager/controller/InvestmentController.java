package com.example.PortfolioManager.controller;

import com.example.PortfolioManager.dto.InvestmentRequestDTO;
import com.example.PortfolioManager.model.Investment;
import com.example.PortfolioManager.service.InvestmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/investment")
@RequiredArgsConstructor
public class InvestmentController {

    private final InvestmentService investmentService;

    @PostMapping
    public ResponseEntity<Investment> create(@RequestBody @Valid InvestmentRequestDTO request) {
        return ResponseEntity.ok(investmentService.createInvestment(request));
    }
}