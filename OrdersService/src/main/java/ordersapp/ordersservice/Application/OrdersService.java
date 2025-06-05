package ordersapp.ordersservice.Application;

import lombok.RequiredArgsConstructor;
import ordersapp.ordersservice.Domain.Entities.Order;
import ordersapp.ordersservice.Domain.Enums.OrderStatus;
import ordersapp.ordersservice.Domain.Interfaces.Repositories.OrdersRepositoryI;
import ordersapp.ordersservice.Domain.Interfaces.Services.OrdersServiceI;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrdersService implements OrdersServiceI {
    private final OrdersRepositoryI ordersRepository;

    @Override
    public List<Order> getOrdersById(Long userId) {
        return ordersRepository.getOrdersByUserId(userId);
    }

    @Override
    public String getOrderStatusById(Long orderId) {
        Order ord = ordersRepository.getOrderById(orderId);
        return ord.getStatus().name();
    }

    @Override
    public Long createOrder(Order order) {
        order.setStatus(OrderStatus.NEW);
        var ordId = ordersRepository.save(order).getId();
        //Payment logic
        return ordId;
    }
}
