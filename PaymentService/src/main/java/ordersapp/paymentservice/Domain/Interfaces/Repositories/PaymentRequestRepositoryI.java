package ordersapp.paymentservice.Domain.Interfaces.Repositories;

import ordersapp.paymentservice.Domain.Kafka.Inbox.InboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaymentRequestRepositoryI extends JpaRepository<InboxEvent, Long> {
    List<InboxEvent> findAllByReceivedFalseOrderByCreatedAtAsc();
}
