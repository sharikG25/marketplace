package com.marketplace.paymentservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class PaymentRequest {

    @NotNull(message = "El id de la orden es obligatorio")
    private Long orderId;

    @NotNull(message = "El id del usuario es obligatorio")
    private Long userId;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0")
    private BigDecimal amount;

    @NotNull(message = "El método de pago es obligatorio")
    private String method;

    private String notes;
}