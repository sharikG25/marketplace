package com.marketplace.analyticsservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class SaleRecordRequest {

    @NotNull(message = "El id de la orden es obligatorio")
    private Long orderId;

    @NotNull(message = "El id del producto es obligatorio")
    private Long productId;

    @NotBlank(message = "El nombre del producto es obligatorio")
    private String productName;

    @NotBlank(message = "La categoría es obligatoria")
    private String category;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad mínima es 1")
    private Integer quantity;

    @NotNull(message = "El precio unitario es obligatorio")
    private BigDecimal unitPrice;

    @NotNull(message = "El id del usuario es obligatorio")
    private Long userId;
}