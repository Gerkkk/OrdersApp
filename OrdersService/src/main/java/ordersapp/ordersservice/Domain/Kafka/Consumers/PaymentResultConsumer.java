package ordersapp.ordersservice.Domain.Kafka.Consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ordersapp.ordersservice.Application.OrdersService;
import ordersapp.ordersservice.Domain.Enums.OrderStatus;
import ordersapp.ordersservice.Domain.Kafka.Events.PaymentResultEvent;
import ordersapp.ordersservice.Domain.Redis.OrderStatusPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class PaymentResultConsumer {
    private final OrderStatusPublisher orderStatusPublisher;
    private final OrdersService ordersService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "payment-results", groupId = "orders")
    public void handlePaymentResult(String json) {
        try {
            PaymentResultEvent event = objectMapper.readValue(json, PaymentResultEvent.class);
            var orderOptional = ordersService.getOrderById(event.orderId());

            if (orderOptional.isEmpty()) {
                return;
            }

            var order = orderOptional.get();
            if (event.isSuccessful()) {
                order.setStatus(OrderStatus.FINISHED);
                ordersService.updateOrder(order);
                orderStatusPublisher.publishStatusUpdate(String.valueOf(order.getId()), OrderStatus.FINISHED.name());
            } else {
                order.setStatus(OrderStatus.CANCELLED);
                ordersService.updateOrder(order);
                orderStatusPublisher.publishStatusUpdate(String.valueOf(order.getId()), OrderStatus.CANCELLED.name());
            }

        } catch (Exception e) {
            log.error("Failed to serialize customer event: {}",e.getMessage());
        }
    }
}
