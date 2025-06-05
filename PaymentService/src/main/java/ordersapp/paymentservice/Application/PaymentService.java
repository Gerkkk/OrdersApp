package ordersapp.paymentservice.Application;

import lombok.RequiredArgsConstructor;
import ordersapp.paymentservice.Domain.Entities.Account;
import ordersapp.paymentservice.Domain.Interfaces.Repositories.AccountRepositoryI;
import ordersapp.paymentservice.Domain.Interfaces.Services.AccountServiceI;
import org.apache.logging.log4j.util.Cast;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class PaymentService implements AccountServiceI {
    private final AccountRepositoryI accountRepository;

    @Override
    public BigDecimal getBalance(Long accountId) {
        var account = accountRepository.findByUserId(accountId);

        if (account == null) {
            return null;
        } else {
            return account.getBalance();
        }
    }

    @Override
    public Long createAccount(Long userId) {
        var account = accountRepository.findByUserId(userId);

        if (account != null) {
            return Long.valueOf("-1");
        } else {
            Account acc = Account.builder().userId(userId).balance(BigDecimal.ZERO).build();
            var newAcc = accountRepository.save(acc);
            return newAcc.getId();
        }
    }

    @Override
    public BigDecimal deposit(Long userId, BigDecimal amount) {
        var account = accountRepository.findByUserId(userId);

        if (account != null) {
            account.setBalance(account.getBalance().add(amount));
            return accountRepository.save(account).getBalance();
        } else {
            return BigDecimal.valueOf(-1);
        }
    }
}
