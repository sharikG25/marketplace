package com.marketplace.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserProfileRequest {

    @NotNull(message = "El id del usuario es obligatorio")
    private Long userId;

    @Email(message = "Formato de email inválido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    private String phone;
    private String address;
    private String city;
    private String country;
    private String zipCode;
    private String role;
}