package ordersapp.ordersservice.Domain.Redis;

import lombok.RequiredArgsConstructor;
import ordersapp.ordersservice.Presentation.WebSocketController;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderStatusSubscriber implements MessageListener {

    private final WebSocketController sessionStore;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String payload = new String(message.getBody());
        String[] parts = payload.split(":");
        String orderId = parts[0];
        String status = parts[1];
        sessionStore.sendStatusUpdate(orderId, status);
    }
}

