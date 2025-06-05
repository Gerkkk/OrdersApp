package ordersapp.paymentservice.Domain.Interfaces.Services;

import java.math.BigDecimal;

public interface AccountServiceI {
    BigDecimal getBalance(Long accountId);
    Long createAccount(Long userId);
    BigDecimal deposit(Long userId, BigDecimal amount);
}
