package com.marketplace.notificationservice.controller;

import com.marketplace.notificationservice.dto.NotificationRequest;
import com.marketplace.notificationservice.dto.NotificationResponse;
import com.marketplace.notificationservice.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<NotificationResponse> send(
            @Valid @RequestBody NotificationRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(notificationService.send(request));
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getAll() {
        return ResponseEntity.ok(notificationService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponse> getById(
            @PathVariable Long id) {
        return ResponseEntity.ok(notificationService.getById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<List<NotificationResponse>> getByEmail(
            @PathVariable String email) {
        return ResponseEntity.ok(notificationService.getByEmail(email));
    }

    @GetMapping("/failed")
    public ResponseEntity<List<NotificationResponse>> getFailed() {
        return ResponseEntity.ok(notificationService.getFailed());
    }
}