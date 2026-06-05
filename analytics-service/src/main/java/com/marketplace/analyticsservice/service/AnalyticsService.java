package com.marketplace.analyticsservice.service;

import com.marketplace.analyticsservice.dto.*;
import com.marketplace.analyticsservice.entity.SaleRecord;
import com.marketplace.analyticsservice.repository.SaleRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final SaleRecordRepository saleRecordRepository;

    public SaleRecordResponse registerSale(SaleRecordRequest request) {
        BigDecimal total = request.getUnitPrice()
                .multiply(BigDecimal.valueOf(request.getQuantity()));

        SaleRecord record = SaleRecord.builder()
                .orderId(request.getOrderId())
                .productId(request.getProductId())
                .productName(request.getProductName())
                .category(request.getCategory())
                .quantity(request.getQuantity())
                .unitPrice(request.getUnitPrice())
                .total(total)
                .userId(request.getUserId())
                .build();

        return toResponse(saleRecordRepository.save(record));
    }

    public List<SaleRecordResponse> getAll() {
        return saleRecordRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<SaleRecordResponse> getByUserId(Long userId) {
        return saleRecordRepository.findByUserId(userId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<CategorySalesResponse> getSalesByCategory() {
        return saleRecordRepository.salesByCategory()
                .stream()
                .map(row -> CategorySalesResponse.builder()
                        .category((String) row[0])
                        .totalSales((BigDecimal) row[1])
                        .build())
                .collect(Collectors.toList());
    }

    public List<TopProductResponse> getTopProducts() {
        return saleRecordRepository.topProducts()
                .stream()
                .map(row -> TopProductResponse.builder()
                        .productId((Long) row[0])
                        .productName((String) row[1])
                        .totalQuantitySold(((Number) row[2]).longValue())
                        .build())
                .collect(Collectors.toList());
    }

    public BigDecimal getTotalRevenue() {
        BigDecimal revenue = saleRecordRepository.totalRevenue();
        return revenue != null ? revenue : BigDecimal.ZERO;
    }

    public BigDecimal getRevenueByPeriod(
            LocalDateTime start, LocalDateTime end) {
        BigDecimal revenue = saleRecordRepository.revenueByPeriod(start, end);
        return revenue != null ? revenue : BigDecimal.ZERO;
    }

    public List<SaleRecordResponse> getByPeriod(
            LocalDateTime start, LocalDateTime end) {
        return saleRecordRepository.findByCreatedAtBetween(start, end)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private SaleRecordResponse toResponse(SaleRecord record) {
        return SaleRecordResponse.builder()
                .id(record.getId())
                .orderId(record.getOrderId())
                .productId(record.getProductId())
                .productName(record.getProductName())
                .category(record.getCategory())
                .quantity(record.getQuantity())
                .unitPrice(record.getUnitPrice())
                .total(record.getTotal())
                .userId(record.getUserId())
                .createdAt(record.getCreatedAt())
                .build();
    }
}