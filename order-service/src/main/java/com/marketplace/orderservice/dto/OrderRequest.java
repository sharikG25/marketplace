package com.marketplace.orderservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class OrderRequest {

    @NotNull(message = "El id del usuario es obligatorio")
    private Long userId;

    @NotNull(message = "El email del usuario es obligatorio")
    private String userEmail;

    private String shippingAddress;

    @NotEmpty(message = "La orden debe tener al menos un producto")
    private List<OrderItemRequest> items;
}