package com.example.PortfolioManager.controller;

import com.example.PortfolioManager.dto.SavingRequestDTO;
import com.example.PortfolioManager.model.Saving;
import com.example.PortfolioManager.service.SavingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/saving")
@RequiredArgsConstructor
public class SavingController {

    private final SavingService savingService;

    @PostMapping
    public ResponseEntity<Saving> create(@RequestBody @Valid SavingRequestDTO request) {
        return ResponseEntity.ok(savingService.createSaving(request));
    }
}