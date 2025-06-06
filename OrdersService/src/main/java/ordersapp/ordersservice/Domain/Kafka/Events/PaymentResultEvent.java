package ordersapp.ordersservice.Domain.Kafka.Events;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public record PaymentResultEvent(
        Long orderId,
        boolean success
) {}
