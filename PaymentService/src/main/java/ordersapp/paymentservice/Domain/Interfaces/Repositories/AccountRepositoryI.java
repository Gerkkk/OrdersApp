package ordersapp.paymentservice.Domain.Interfaces.Repositories;

import ordersapp.paymentservice.Domain.Entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepositoryI extends JpaRepository<Account, Long> {
    Account findByUserId(Long userId);
}
