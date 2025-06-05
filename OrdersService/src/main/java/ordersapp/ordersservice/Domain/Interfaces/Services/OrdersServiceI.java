package ordersapp.ordersservice.Domain.Interfaces.Services;

import ordersapp.ordersservice.Domain.Entities.Order;

import java.util.List;

public interface OrdersServiceI {
    List<Order> getOrdersById(Long userId);
    String getOrderStatusById(Long orderId);
    Long createOrder(Order order);
}
