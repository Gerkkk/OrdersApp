package ordersapp.ordersservice.Domain.Kafka.Events;

public record PaymentResultEvent(
        Long orderId,
        boolean isSuccessful
) {}
