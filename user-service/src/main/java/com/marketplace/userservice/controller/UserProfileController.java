package com.marketplace.userservice.controller;

import com.marketplace.userservice.dto.UserProfileRequest;
import com.marketplace.userservice.dto.UserProfileResponse;
import com.marketplace.userservice.dto.UserProfileUpdateRequest;
import com.marketplace.userservice.service.UserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PostMapping
    public ResponseEntity<UserProfileResponse> create(
            @Valid @RequestBody UserProfileRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userProfileService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<UserProfileResponse>> getAll() {
        return ResponseEntity.ok(userProfileService.getAll());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserProfileResponse> getByUserId(
            @PathVariable Long userId) {
        return ResponseEntity.ok(userProfileService.getByUserId(userId));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserProfileResponse> getByEmail(
            @PathVariable String email) {
        return ResponseEntity.ok(userProfileService.getByEmail(email));
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<UserProfileResponse> update(
            @PathVariable Long userId,
            @Valid @RequestBody UserProfileUpdateRequest request) {
        return ResponseEntity.ok(userProfileService.update(userId, request));
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deactivate(@PathVariable Long userId) {
        userProfileService.deactivate(userId);
        return ResponseEntity.noContent().build();
    }
}