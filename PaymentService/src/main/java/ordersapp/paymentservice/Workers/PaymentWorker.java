package ordersapp.paymentservice.Workers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ordersapp.paymentservice.Domain.Interfaces.Repositories.PaymentRequestRepositoryI;
import ordersapp.paymentservice.Domain.Interfaces.Repositories.PaymentResultRepositoryI;
import ordersapp.paymentservice.Domain.Interfaces.Services.AccountServiceI;
import ordersapp.paymentservice.Domain.Kafka.Events.PaymentRequiredEvent;
import ordersapp.paymentservice.Domain.Kafka.Events.PaymentResultEvent;
import ordersapp.paymentservice.Domain.Kafka.Outbox.OutboxEvent;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentWorker {
    private final AccountServiceI accountService;
    private final PaymentRequestRepositoryI paymentRequestRepository;
    private final PaymentResultRepositoryI paymentResultRepository;
    private final ObjectMapper objectMapper;


    @Transactional
    @Scheduled(fixedDelay = 1000)
    void processPaymentRequests() {
        var inboxEvents = paymentRequestRepository.findAllByReceivedFalseOrderByCreatedAtAsc();

        for (var inboxEvent : inboxEvents) {
            try {
                var mapped = objectMapper.readValue(inboxEvent.getPayload(), PaymentRequiredEvent.class);
                inboxEvent.setReceived(true);

                var balance = accountService.getBalance(mapped.userId());

                PaymentResultEvent paymentResultEvent = null;

                if (Objects.equals(balance, BigDecimal.valueOf(-1)) || balance.compareTo(mapped.price()) < 0 ) {
                    paymentResultEvent = new PaymentResultEvent(mapped.orderId(), false);
                } else {
                    paymentResultEvent = new PaymentResultEvent(mapped.orderId(), true);
                    accountService.deposit(mapped.userId(), mapped.price().negate());
                }

                paymentRequestRepository.save(inboxEvent);

                PaymentResultEvent event = new PaymentResultEvent(paymentResultEvent.orderId(), paymentResultEvent.isSuccessful());
                OutboxEvent outboxEvent = new OutboxEvent();

                outboxEvent.setCreatedAt(LocalDateTime.now());

                try {
                    outboxEvent.setPayload(objectMapper.writeValueAsString(event));
                } catch (Exception e) {
                    log.error("Failed to serialize customer event{}", e.getMessage());
                }

                paymentResultRepository.save(outboxEvent);
            } catch (Exception e) {
                log.error("Error while reading payment request from kafka: {}", e.getMessage());
            }
        }
    }
}
