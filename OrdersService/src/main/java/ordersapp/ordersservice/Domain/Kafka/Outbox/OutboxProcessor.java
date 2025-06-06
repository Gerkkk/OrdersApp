package ordersapp.ordersservice.Domain.Kafka.Outbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ordersapp.ordersservice.Domain.Interfaces.Repositories.OutboxEventRepositoryI;
import ordersapp.ordersservice.Domain.Kafka.Events.PaymentRequiredEvent;
import ordersapp.ordersservice.Domain.Kafka.Producers.PaymentRequiredProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxProcessor {
    private final PaymentRequiredProducer kafkaProducer;
    private final OutboxEventRepositoryI outboxEventRepository;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedDelay = 1000)
    public void process() {
        var outboxEvents = outboxEventRepository.findAllBySentFalseOrderByCreatedAtAsc();

        for (var outboxEvent : outboxEvents) {
            try {
                var mapped = objectMapper.readValue(outboxEvent.getPayload(), PaymentRequiredEvent.class);
                kafkaProducer.sendPaymentRequest(mapped);
            } catch (Exception e) {
                System.out.println("Error while sending payment request via kafka: " + e.getMessage());
            }

            outboxEvent.setSent(true);
            outboxEventRepository.save(outboxEvent);
        }
    }
}


