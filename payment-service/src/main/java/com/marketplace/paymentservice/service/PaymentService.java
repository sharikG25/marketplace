package com.marketplace.paymentservice.service;

import com.marketplace.paymentservice.dto.PaymentRequest;
import com.marketplace.paymentservice.dto.PaymentResponse;
import com.marketplace.paymentservice.entity.Payment;
import com.marketplace.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional
    public PaymentResponse processPayment(PaymentRequest request) {
        if (paymentRepository.findByOrderId(request.getOrderId()).isPresent()) {
            throw new RuntimeException("Ya existe un pago para la orden: "
                    + request.getOrderId());
        }

        Payment.PaymentMethod method;
        try {
            method = Payment.PaymentMethod.valueOf(
                    request.getMethod().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Método de pago inválido: "
                    + request.getMethod());
        }

        boolean approved = simulatePayment(request.getAmount());

        Payment payment = Payment.builder()
                .orderId(request.getOrderId())
                .userId(request.getUserId())
                .amount(request.getAmount())
                .method(method)
                .status(approved
                        ? Payment.PaymentStatus.APPROVED
                        : Payment.PaymentStatus.REJECTED)
                .transactionReference(UUID.randomUUID().toString())
                .notes(request.getNotes())
                .build();

        return toResponse(paymentRepository.save(payment));
    }

    public PaymentResponse getByOrderId(Long orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException(
                        "Pago no encontrado para orden: " + orderId));
        return toResponse(payment);
    }

    public PaymentResponse getById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Pago no encontrado con id: " + id));
        return toResponse(payment);
    }

    public List<PaymentResponse> getByUserId(Long userId) {
        return paymentRepository.findByUserId(userId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public PaymentResponse refund(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Pago no encontrado con id: " + id));

        if (payment.getStatus() != Payment.PaymentStatus.APPROVED) {
            throw new RuntimeException(
                    "Solo se pueden reembolsar pagos aprobados");
        }

        payment.setStatus(Payment.PaymentStatus.REFUNDED);
        return toResponse(paymentRepository.save(payment));
    }

    private boolean simulatePayment(java.math.BigDecimal amount) {
        return amount.compareTo(new java.math.BigDecimal("10000")) < 0;
    }

    private PaymentResponse toResponse(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .orderId(payment.getOrderId())
                .userId(payment.getUserId())
                .amount(payment.getAmount())
                .status(payment.getStatus().name())
                .method(payment.getMethod().name())
                .transactionReference(payment.getTransactionReference())
                .notes(payment.getNotes())
                .createdAt(payment.getCreatedAt())
                .build();
    }
}