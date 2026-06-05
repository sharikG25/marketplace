package com.marketplace.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {

    private Long id;
    private Long userId;
    private String email;
    private String name;
    private String phone;
    private String address;
    private String city;
    private String country;
    private String zipCode;
    private String role;
    private boolean active;
    private LocalDateTime createdAt;
}