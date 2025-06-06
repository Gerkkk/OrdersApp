package ordersapp.ordersservice.Domain.Kafka.Producers;

import ordersapp.ordersservice.Domain.Kafka.Events.PaymentRequiredEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentRequiredProducer {
    @Autowired
    private KafkaTemplate<String, PaymentRequiredEvent> kafkaTemplate;

    public void sendPaymentRequest(PaymentRequiredEvent event) {
        kafkaTemplate.send(
                "payment_request",
                String.valueOf(event.userId()),
                event
        );
    }
}