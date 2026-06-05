package com.marketplace.analyticsservice.repository;

import com.marketplace.analyticsservice.entity.SaleRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SaleRecordRepository
        extends JpaRepository<SaleRecord, Long> {

    List<SaleRecord> findByUserId(Long userId);

    List<SaleRecord> findByCategory(String category);

    List<SaleRecord> findByCreatedAtBetween(
            LocalDateTime start, LocalDateTime end);

    @Query("SELECT s.category, SUM(s.total) FROM SaleRecord s " +
            "GROUP BY s.category ORDER BY SUM(s.total) DESC")
    List<Object[]> salesByCategory();

    @Query("SELECT s.productId, s.productName, SUM(s.quantity) " +
            "FROM SaleRecord s GROUP BY s.productId, s.productName " +
            "ORDER BY SUM(s.quantity) DESC")
    List<Object[]> topProducts();

    @Query("SELECT SUM(s.total) FROM SaleRecord s")
    BigDecimal totalRevenue();

    @Query("SELECT SUM(s.total) FROM SaleRecord s " +
            "WHERE s.createdAt BETWEEN :start AND :end")
    BigDecimal revenueByPeriod(
            @org.springframework.data.repository.query.Param("start")
            LocalDateTime start,
            @org.springframework.data.repository.query.Param("end")
            LocalDateTime end);
}