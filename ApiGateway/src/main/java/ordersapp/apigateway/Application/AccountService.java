package ordersapp.apigateway.Application;

import lombok.RequiredArgsConstructor;
import ordersapp.apigateway.Domain.Interfaces.Application.AccountsServiceI;
import ordersapp.apigateway.Domain.Interfaces.Transport.AccountTranportI;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.rmi.ConnectException;

@Component
@RequiredArgsConstructor
public class AccountService implements AccountsServiceI {
    private final AccountTranportI accountTranport;

    @Override
    public BigDecimal getAccountBalance(long accountId) throws ConnectException {
        //There in future we can add checking if the user has rights to get the balance of this account. By calling authorisation service, for example.
        return accountTranport.getAccountBalance(accountId);
    }

    @Override
    public long createAccount(long userId) throws ConnectException {
        //There in future we can add checking if the user has rights to create an account for the user given. By calling authorisation service, for example.
        return accountTranport.createAccount(userId);
    }

    @Override
    public BigDecimal deposit(long userId, BigDecimal amount) throws ConnectException {
        //There in future we can add checking if the user has rights to deposit to the account given. By calling authorisation service, for example.
        return accountTranport.deposit(userId, amount);
    }
}
