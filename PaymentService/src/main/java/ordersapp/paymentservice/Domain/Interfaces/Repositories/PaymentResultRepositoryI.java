package ordersapp.paymentservice.Domain.Interfaces.Repositories;

import ordersapp.paymentservice.Domain.Kafka.Outbox.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentResultRepositoryI extends JpaRepository<OutboxEvent, Long> {
    List<OutboxEvent> findAllBySentFalseOrderByCreatedAtAsc();
}
