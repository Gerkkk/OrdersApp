package ordersapp.ordersservice.Domain.Kafka.Producers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ordersapp.ordersservice.Domain.Kafka.Events.PaymentRequiredEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentRequiredProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendPaymentRequest(PaymentRequiredEvent event) {
        try {
            String json = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(
                    "payment_requests",
                    String.valueOf(event.userId()),
                    json
            );
        } catch (Exception e) {
            log.error("Error while sending payment request: {}", e.getMessage());
        }

    }
}