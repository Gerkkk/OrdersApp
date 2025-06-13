package ordersapp.ordersservice.Application;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ordersapp.ordersservice.Domain.Entities.Order;
import ordersapp.ordersservice.Domain.Enums.OrderStatus;
import ordersapp.ordersservice.Domain.Interfaces.Repositories.OrdersRepositoryI;
import ordersapp.ordersservice.Domain.Interfaces.Repositories.OutboxEventRepositoryI;
import ordersapp.ordersservice.Domain.Interfaces.Services.OrdersServiceI;
import ordersapp.ordersservice.Domain.Kafka.Events.PaymentRequiredEvent;
import ordersapp.ordersservice.Domain.Kafka.Outbox.OutboxEvent;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrdersService implements OrdersServiceI {
    private final OrdersRepositoryI ordersRepository;
    private final OutboxEventRepositoryI outboxEventRepository;
    private final ObjectMapper objectMapper;

    private void saveToOutbox(Order order) {
        PaymentRequiredEvent event = new PaymentRequiredEvent(order.getUserId(), order.getAmount(), order.getId());
        OutboxEvent outboxEvent = new OutboxEvent();

        outboxEvent.setCreatedAt(LocalDateTime.now());

        try {
            outboxEvent.setPayload(objectMapper.writeValueAsString(event));
        } catch (Exception e) {
            throw new InternalException("Failed to serialize customer event");
        }

        outboxEventRepository.save(outboxEvent);
    }
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
    @Transactional
    public Long createOrder(Order order) {
        order.setStatus(OrderStatus.NEW);
        var ordId = ordersRepository.save(order).getId();
        saveToOutbox(order);
        return ordId;
    }

    @Override
    public void updateOrder(Order order) {
        ordersRepository.save(order);
    }

    @Override
    public Optional<Order> getOrderById(Long orderId) {
        return Optional.ofNullable(ordersRepository.getOrderById(orderId));
    }
}
