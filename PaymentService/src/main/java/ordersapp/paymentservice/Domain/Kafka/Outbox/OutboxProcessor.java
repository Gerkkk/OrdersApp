package ordersapp.paymentservice.Domain.Kafka.Outbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ordersapp.paymentservice.Domain.Interfaces.Repositories.PaymentResultRepositoryI;
import ordersapp.paymentservice.Domain.Kafka.Events.PaymentResultEvent;
import ordersapp.paymentservice.Domain.Kafka.Producers.PaymentResultProducer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxProcessor {
    private final PaymentResultProducer kafkaProducer;
    private final PaymentResultRepositoryI outboxEventRepository;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedDelay = 1000)
    public void process() {
        var outboxEvents = outboxEventRepository.findAllBySentFalseOrderByCreatedAtAsc();

        for (var outboxEvent : outboxEvents) {
            try {
                var mapped = objectMapper.readValue(outboxEvent.getPayload(), PaymentResultEvent.class);
                kafkaProducer.sendPaymentRequest(mapped);
                outboxEvent.setSent(true);
                outboxEventRepository.save(outboxEvent);
            } catch (Exception e) {
                log.error("Error while sending payment request via kafka: {}", e.getMessage());
            }
        }
    }
}


