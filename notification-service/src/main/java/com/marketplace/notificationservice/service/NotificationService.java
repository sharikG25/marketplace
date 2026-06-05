package com.marketplace.notificationservice.service;

import com.marketplace.notificationservice.dto.NotificationRequest;
import com.marketplace.notificationservice.dto.NotificationResponse;
import com.marketplace.notificationservice.entity.Notification;
import com.marketplace.notificationservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final JavaMailSender mailSender;

    @Transactional
    public NotificationResponse send(NotificationRequest request) {

        Notification.NotificationType type;
        try {
            type = Notification.NotificationType.valueOf(
                    request.getType().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Tipo de notificación inválido: "
                    + request.getType());
        }

        Notification notification = Notification.builder()
                .recipientEmail(request.getRecipientEmail())
                .subject(request.getSubject())
                .body(request.getBody())
                .type(type)
                .status(Notification.NotificationStatus.PENDING)
                .build();

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(request.getRecipientEmail());
            message.setSubject(request.getSubject());
            message.setText(request.getBody());
            mailSender.send(message);

            notification.setStatus(Notification.NotificationStatus.SENT);
            notification.setSentAt(LocalDateTime.now());
            log.info("Email enviado a: {}", request.getRecipientEmail());

        } catch (Exception e) {
            notification.setStatus(Notification.NotificationStatus.FAILED);
            notification.setErrorMessage(e.getMessage());
            log.error("Error enviando email a {}: {}",
                    request.getRecipientEmail(), e.getMessage());
        }

        return toResponse(notificationRepository.save(notification));
    }

    public List<NotificationResponse> getByEmail(String email) {
        return notificationRepository.findByRecipientEmail(email)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<NotificationResponse> getAll() {
        return notificationRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public NotificationResponse getById(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Notificación no encontrada con id: " + id));
        return toResponse(notification);
    }

    public List<NotificationResponse> getFailed() {
        return notificationRepository
                .findByStatus(Notification.NotificationStatus.FAILED)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private NotificationResponse toResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .recipientEmail(notification.getRecipientEmail())
                .subject(notification.getSubject())
                .body(notification.getBody())
                .status(notification.getStatus().name())
                .type(notification.getType().name())
                .errorMessage(notification.getErrorMessage())
                .createdAt(notification.getCreatedAt())
                .sentAt(notification.getSentAt())
                .build();
    }
}