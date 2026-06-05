package com.marketplace.notificationservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NotificationRequest {

    @Email(message = "Formato de email inválido")
    @NotBlank(message = "El email del destinatario es obligatorio")
    private String recipientEmail;

    @NotBlank(message = "El asunto es obligatorio")
    private String subject;

    @NotBlank(message = "El cuerpo del mensaje es obligatorio")
    private String body;

    @NotNull(message = "El tipo de notificación es obligatorio")
    private String type;
}