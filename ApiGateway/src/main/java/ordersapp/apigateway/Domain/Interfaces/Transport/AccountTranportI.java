package ordersapp.apigateway.Domain.Interfaces.Transport;

import java.math.BigDecimal;
import java.rmi.ConnectException;

public interface AccountTranportI {
    BigDecimal getAccountBalance(long accountId) throws ConnectException;
    long createAccount(long userId) throws ConnectException;
    BigDecimal deposit(long userId, BigDecimal amount) throws ConnectException;
}
