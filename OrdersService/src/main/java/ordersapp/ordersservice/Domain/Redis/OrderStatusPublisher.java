package ordersapp.ordersservice.Domain.Redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderStatusPublisher {
    private final RedisTemplate<String, String> redisTemplate;

    public void publishStatusUpdate(String orderId, String status) {
        System.out.println("Publishing status update for orderId: " + orderId + " status: " + status);
        String message = orderId + ":" + status;
        redisTemplate.convertAndSend("order-status-channel", message);
    }
}

