package com.marketplace.userservice.dto;

import lombok.Data;

@Data
public class UserProfileUpdateRequest {

    private String name;
    private String phone;
    private String address;
    private String city;
    private String country;
    private String zipCode;
}