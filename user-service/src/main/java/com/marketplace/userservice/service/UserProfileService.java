package com.marketplace.userservice.service;

import com.marketplace.userservice.dto.UserProfileRequest;
import com.marketplace.userservice.dto.UserProfileResponse;
import com.marketplace.userservice.dto.UserProfileUpdateRequest;
import com.marketplace.userservice.entity.UserProfile;
import com.marketplace.userservice.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfileResponse create(UserProfileRequest request) {
        if (userProfileRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Ya existe un perfil con el email: "
                    + request.getEmail());
        }

        UserProfile.UserRole role;
        try {
            role = request.getRole() != null
                    ? UserProfile.UserRole.valueOf(request.getRole().toUpperCase())
                    : UserProfile.UserRole.CUSTOMER;
        } catch (IllegalArgumentException e) {
            role = UserProfile.UserRole.CUSTOMER;
        }

        UserProfile profile = UserProfile.builder()
                .userId(request.getUserId())
                .email(request.getEmail())
                .name(request.getName())
                .phone(request.getPhone())
                .address(request.getAddress())
                .city(request.getCity())
                .country(request.getCountry())
                .zipCode(request.getZipCode())
                .role(role)
                .active(true)
                .build();

        return toResponse(userProfileRepository.save(profile));
    }

    public UserProfileResponse getByUserId(Long userId) {
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException(
                        "Perfil no encontrado para usuario: " + userId));
        return toResponse(profile);
    }

    public UserProfileResponse getByEmail(String email) {
        UserProfile profile = userProfileRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException(
                        "Perfil no encontrado para email: " + email));
        return toResponse(profile);
    }

    public List<UserProfileResponse> getAll() {
        return userProfileRepository.findByActiveTrue()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserProfileResponse update(Long userId,
                                      UserProfileUpdateRequest request) {
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException(
                        "Perfil no encontrado para usuario: " + userId));

        if (request.getName() != null)
            profile.setName(request.getName());
        if (request.getPhone() != null)
            profile.setPhone(request.getPhone());
        if (request.getAddress() != null)
            profile.setAddress(request.getAddress());
        if (request.getCity() != null)
            profile.setCity(request.getCity());
        if (request.getCountry() != null)
            profile.setCountry(request.getCountry());
        if (request.getZipCode() != null)
            profile.setZipCode(request.getZipCode());

        return toResponse(userProfileRepository.save(profile));
    }

    @Transactional
    public void deactivate(Long userId) {
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException(
                        "Perfil no encontrado para usuario: " + userId));
        profile.setActive(false);
        userProfileRepository.save(profile);
    }

    private UserProfileResponse toResponse(UserProfile profile) {
        return UserProfileResponse.builder()
                .id(profile.getId())
                .userId(profile.getUserId())
                .email(profile.getEmail())
                .name(profile.getName())
                .phone(profile.getPhone())
                .address(profile.getAddress())
                .city(profile.getCity())
                .country(profile.getCountry())
                .zipCode(profile.getZipCode())
                .role(profile.getRole().name())
                .active(profile.isActive())
                .createdAt(profile.getCreatedAt())
                .build();
    }
}