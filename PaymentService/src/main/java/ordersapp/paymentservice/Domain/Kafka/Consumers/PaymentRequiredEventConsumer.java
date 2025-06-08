package ordersapp.paymentservice.Domain.Kafka.Consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import ordersapp.paymentservice.Domain.Interfaces.Repositories.PaymentRequestRepositoryI;
import ordersapp.paymentservice.Domain.Kafka.Events.PaymentRequiredEvent;
import ordersapp.paymentservice.Domain.Kafka.Inbox.InboxEvent;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PaymentRequiredEventConsumer {
    private final PaymentRequestRepositoryI paymentRequestRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "payment_requests", groupId = "orders")
    public void handlePaymentRequest(String json) {
        InboxEvent inboxEvent = new InboxEvent();

        try {
            PaymentRequiredEvent event = objectMapper.readValue(json, PaymentRequiredEvent.class);
            inboxEvent.setCreatedAt(LocalDateTime.now());
            inboxEvent.setPayload(json);
        } catch (Exception e) {
            throw new InternalException("Failed to serialize customer event");
        }

        paymentRequestRepository.save(inboxEvent);
    }
}
