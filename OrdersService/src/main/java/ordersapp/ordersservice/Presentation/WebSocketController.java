package ordersapp.ordersservice.Presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WebSocketController extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> orderSessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String orderId = getOrderIdFromQuery(session);
        log.info("Connection established for {}", orderId);
        orderSessions.put(orderId, session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        orderSessions.values().remove(session);
    }

    public void sendStatusUpdate(String orderId, String status) {
        WebSocketSession session = orderSessions.get(orderId);
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(status));
            } catch (IOException e) {
                log.error("Error sending message to order: {}; status: {}", orderId, status);
            }
        } else {
            log.error("Connection for Order {} not found", orderId);
        }
    }

    private String getOrderIdFromQuery(WebSocketSession session) {
        URI uri = session.getUri();
        if (uri != null && uri.getQuery() != null) {
            return Arrays.stream(uri.getQuery().split("&"))
                    .map(s -> s.split("="))
                    .filter(kv -> kv[0].equals("orderId"))
                    .map(kv -> kv[1])
                    .findFirst().orElse("");
        }
        return "";
    }
}
