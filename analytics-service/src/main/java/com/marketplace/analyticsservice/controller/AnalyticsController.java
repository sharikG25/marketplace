package com.marketplace.analyticsservice.controller;

import com.marketplace.analyticsservice.dto.*;
import com.marketplace.analyticsservice.service.AnalyticsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @PostMapping("/sales")
    public ResponseEntity<SaleRecordResponse> registerSale(
            @Valid @RequestBody SaleRecordRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(analyticsService.registerSale(request));
    }

    @GetMapping("/sales")
    public ResponseEntity<List<SaleRecordResponse>> getAll() {
        return ResponseEntity.ok(analyticsService.getAll());
    }

    @GetMapping("/sales/user/{userId}")
    public ResponseEntity<List<SaleRecordResponse>> getByUserId(
            @PathVariable Long userId) {
        return ResponseEntity.ok(analyticsService.getByUserId(userId));
    }

    @GetMapping("/sales/by-category")
    public ResponseEntity<List<CategorySalesResponse>> getSalesByCategory() {
        return ResponseEntity.ok(analyticsService.getSalesByCategory());
    }

    @GetMapping("/sales/top-products")
    public ResponseEntity<List<TopProductResponse>> getTopProducts() {
        return ResponseEntity.ok(analyticsService.getTopProducts());
    }

    @GetMapping("/revenue/total")
    public ResponseEntity<BigDecimal> getTotalRevenue() {
        return ResponseEntity.ok(analyticsService.getTotalRevenue());
    }

    @GetMapping("/revenue/period")
    public ResponseEntity<BigDecimal> getRevenueByPeriod(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime start,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime end) {
        return ResponseEntity.ok(
                analyticsService.getRevenueByPeriod(start, end));
    }

    @GetMapping("/sales/period")
    public ResponseEntity<List<SaleRecordResponse>> getByPeriod(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime start,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime end) {
        return ResponseEntity.ok(
                analyticsService.getByPeriod(start, end));
    }
}