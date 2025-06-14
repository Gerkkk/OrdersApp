package ordersapp.ordersservice.Domain.Redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderStatusPublisher {
    private final RedisTemplate<String, String> redisTemplate;

    public void publishStatusUpdate(String orderId, String status) {
        log.info("Publishing status update for orderId: {} status: {}", orderId, status);
        String message = orderId + ":" + status;
        redisTemplate.convertAndSend("order-status-channel", message);
    }
}

