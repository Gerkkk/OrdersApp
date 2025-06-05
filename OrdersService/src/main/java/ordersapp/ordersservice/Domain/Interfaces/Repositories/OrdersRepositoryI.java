package ordersapp.ordersservice.Domain.Interfaces.Repositories;

import ordersapp.ordersservice.Domain.Entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepositoryI extends JpaRepository<Order, Integer> {
    List<Order> getOrdersByUserId(long userId);
    Order getOrderById(long id);
}
