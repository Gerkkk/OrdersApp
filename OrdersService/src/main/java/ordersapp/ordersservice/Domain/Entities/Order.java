package ordersapp.ordersservice.Domain.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ordersapp.ordersservice.Domain.Enums.OrderStatus;

import java.math.BigDecimal;

@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "orders")
@NoArgsConstructor
public class Order  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @NotNull
    long userId;
    @NotNull
    BigDecimal amount;
    @NotNull
    String payload;
    @NotNull
    OrderStatus status;

    public Order(long id, long userId, BigDecimal amount, String payload, String status) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.payload = payload;
        this.status = OrderStatus.valueOf(status.toUpperCase());
    }
}
