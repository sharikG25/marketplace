package com.marketplace.inventoryservice.service;

import com.marketplace.inventoryservice.dto.InventoryRequest;
import com.marketplace.inventoryservice.dto.InventoryResponse;
import com.marketplace.inventoryservice.dto.StockUpdateRequest;
import com.marketplace.inventoryservice.entity.Inventory;
import com.marketplace.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryResponse create(InventoryRequest request) {
        if (inventoryRepository.existsByProductId(request.getProductId())) {
            throw new RuntimeException("Ya existe inventario para el producto: "
                    + request.getProductId());
        }

        Inventory inventory = Inventory.builder()
                .productId(request.getProductId())
                .productName(request.getProductName())
                .stock(request.getStock())
                .reserved(0)
                .build();

        return toResponse(inventoryRepository.save(inventory));
    }

    public InventoryResponse getByProductId(Long productId) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException(
                        "Inventario no encontrado para producto: " + productId));
        return toResponse(inventory);
    }

    public List<InventoryResponse> getAll() {
        return inventoryRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<InventoryResponse> getLowStock(Integer threshold) {
        return inventoryRepository.findByStockLessThanEqual(threshold)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public InventoryResponse addStock(Long productId, StockUpdateRequest request) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException(
                        "Inventario no encontrado para producto: " + productId));

        inventory.setStock(inventory.getStock() + request.getQuantity());
        return toResponse(inventoryRepository.save(inventory));
    }

    @Transactional
    public InventoryResponse reduceStock(Long productId, StockUpdateRequest request) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException(
                        "Inventario no encontrado para producto: " + productId));

        if (inventory.getAvailable() < request.getQuantity()) {
            throw new RuntimeException("Stock insuficiente. Disponible: "
                    + inventory.getAvailable());
        }

        inventory.setStock(inventory.getStock() - request.getQuantity());
        return toResponse(inventoryRepository.save(inventory));
    }

    @Transactional
    public boolean checkAvailability(Long productId, Integer quantity) {
        return inventoryRepository.findByProductId(productId)
                .map(inv -> inv.getAvailable() >= quantity)
                .orElse(false);
    }

    private InventoryResponse toResponse(Inventory inventory) {
        return InventoryResponse.builder()
                .id(inventory.getId())
                .productId(inventory.getProductId())
                .productName(inventory.getProductName())
                .stock(inventory.getStock())
                .reserved(inventory.getReserved())
                .available(inventory.getAvailable())
                .updatedAt(inventory.getUpdatedAt())
                .build();
    }
}