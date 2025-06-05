package ordersapp.ordersservice.Domain.Enums;

public enum OrderStatus {
    NEW,
    FINISHED,
    CANCELLED;

    String toString(OrderStatus orderStatus) {
        return orderStatus.name();
    }

    OrderStatus fromString(String orderStatus) {
        return OrderStatus.valueOf(orderStatus.toUpperCase());
    }
}
