package ordersapp.apigateway.Domain.Interfaces.Application;

import java.math.BigDecimal;
import java.rmi.ConnectException;

public interface AccountsServiceI {
    BigDecimal getAccountBalance(long accountId) throws ConnectException;
    long createAccount(long userId) throws ConnectException;
    BigDecimal deposit(long userId, BigDecimal amount) throws ConnectException;
}
