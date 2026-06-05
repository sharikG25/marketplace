package com.marketplace.orderservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderItemRequest {

    @NotNull(message = "El id del producto es obligatorio")
    private Long productId;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad mínima es 1")
    private Integer quantity;

    @NotNull(message = "El precio unitario es obligatorio")
    private java.math.BigDecimal unitPrice;

    @NotNull(message = "El nombre del producto es obligatorio")
    private String productName;
}