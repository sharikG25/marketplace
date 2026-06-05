package com.marketplace.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {

    private Long id;
    private String recipientEmail;
    private String subject;
    private String body;
    private String status;
    private String type;
    private String errorMessage;
    private LocalDateTime createdAt;
    private LocalDateTime sentAt;
}