package ordersapp.paymentservice.Domain.Kafka.Events;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

public record PaymentResultEvent(
        Long orderId,
        boolean isSuccessful
) {}
