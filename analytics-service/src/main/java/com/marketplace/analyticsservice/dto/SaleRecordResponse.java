package com.marketplace.analyticsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleRecordResponse {

    private Long id;
    private Long orderId;
    private Long productId;
    private String productName;
    private String category;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal total;
    private Long userId;
    private LocalDateTime createdAt;
}