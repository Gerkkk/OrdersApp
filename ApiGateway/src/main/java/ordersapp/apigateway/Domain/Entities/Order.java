package ordersapp.apigateway.Domain.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ordersapp.apigateway.Domain.Enums.OrderStatus;

import java.math.BigDecimal;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class Order {
    long id;
    long userId;
    BigDecimal amount;
    String payload;
    OrderStatus status;

    public Order(long id, long userId, BigDecimal amount, String payload, String status) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.payload = payload;
        this.status = OrderStatus.valueOf(status.toUpperCase());
    }
}
