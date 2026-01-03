package com.example.PortfolioManager.controller;

import com.example.PortfolioManager.dto.PortfolioRequestDTO;
import com.example.PortfolioManager.model.Portfolio;
import com.example.PortfolioManager.service.impl.PortfolioServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioServiceImpl portfolioService;

    @PostMapping
    public ResponseEntity<Portfolio> create(@RequestBody @Valid PortfolioRequestDTO request) {
        return ResponseEntity.ok(portfolioService.createPortfolio(request));
    }
}