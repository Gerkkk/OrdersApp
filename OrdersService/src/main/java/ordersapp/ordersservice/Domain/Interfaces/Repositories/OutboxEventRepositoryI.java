package ordersapp.ordersservice.Domain.Interfaces.Repositories;

import ordersapp.ordersservice.Domain.Kafka.Outbox.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutboxEventRepositoryI extends JpaRepository<OutboxEvent, Long> {
    List<OutboxEvent> findAllBySentFalseOrderByCreatedAtAsc();
}