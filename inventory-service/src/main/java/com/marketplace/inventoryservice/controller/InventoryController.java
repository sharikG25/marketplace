package com.marketplace.inventoryservice.controller;

import com.marketplace.inventoryservice.dto.InventoryRequest;
import com.marketplace.inventoryservice.dto.InventoryResponse;
import com.marketplace.inventoryservice.dto.StockUpdateRequest;
import com.marketplace.inventoryservice.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<InventoryResponse> create(
            @Valid @RequestBody InventoryRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(inventoryService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<InventoryResponse>> getAll() {
        return ResponseEntity.ok(inventoryService.getAll());
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<InventoryResponse> getByProductId(
            @PathVariable Long productId) {
        return ResponseEntity.ok(inventoryService.getByProductId(productId));
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<InventoryResponse>> getLowStock(
            @RequestParam(defaultValue = "5") Integer threshold) {
        return ResponseEntity.ok(inventoryService.getLowStock(threshold));
    }

    @PatchMapping("/product/{productId}/add")
    public ResponseEntity<InventoryResponse> addStock(
            @PathVariable Long productId,
            @Valid @RequestBody StockUpdateRequest request) {
        return ResponseEntity.ok(inventoryService.addStock(productId, request));
    }

    @PatchMapping("/product/{productId}/reduce")
    public ResponseEntity<InventoryResponse> reduceStock(
            @PathVariable Long productId,
            @Valid @RequestBody StockUpdateRequest request) {
        return ResponseEntity.ok(inventoryService.reduceStock(productId, request));
    }

    @GetMapping("/product/{productId}/check")
    public ResponseEntity<Boolean> checkAvailability(
            @PathVariable Long productId,
            @RequestParam Integer quantity) {
        return ResponseEntity.ok(
                inventoryService.checkAvailability(productId, quantity));
    }
}