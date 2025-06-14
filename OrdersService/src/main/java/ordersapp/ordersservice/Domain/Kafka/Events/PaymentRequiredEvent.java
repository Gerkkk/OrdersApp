package ordersapp.ordersservice.Domain.Kafka.Events;

import java.math.BigDecimal;

public record PaymentRequiredEvent(
        Long userId,
        BigDecimal price,
        Long orderId
) {}
