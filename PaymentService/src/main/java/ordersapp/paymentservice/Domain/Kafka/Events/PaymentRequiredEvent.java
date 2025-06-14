package ordersapp.paymentservice.Domain.Kafka.Events;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.math.BigDecimal;

public record PaymentRequiredEvent(
        Long userId,
        BigDecimal price,
        Long orderId
) {}
