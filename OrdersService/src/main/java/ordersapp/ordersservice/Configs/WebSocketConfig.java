package ordersapp.ordersservice.Configs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ordersapp.ordersservice.Presentation.WebSocketController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
@Slf4j
public class WebSocketConfig implements WebSocketConfigurer {
    private final WebSocketController webSocketController;


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        log.info("Registering websocket handlers");
        registry.addHandler(webSocketController, "/ws/order-status")
                .setAllowedOrigins("*");
    }
}
