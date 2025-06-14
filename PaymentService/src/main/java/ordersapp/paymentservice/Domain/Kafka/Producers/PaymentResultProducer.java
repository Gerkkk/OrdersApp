package ordersapp.paymentservice.Domain.Kafka.Producers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ordersapp.paymentservice.Domain.Kafka.Events.PaymentResultEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentResultProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendPaymentRequest(PaymentResultEvent event) {
        try {
            String json = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(
                    "payment-results",
                    String.valueOf(event.orderId()),
                    json
            );
        } catch (Exception e) {
            log.error("Error while sending payment result: {}", e.getMessage());
        }
    }


}