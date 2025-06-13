package ordersapp.apigateway;

import ordersapp.apigateway.Application.AccountService;
import ordersapp.apigateway.Domain.Interfaces.Transport.AccountTranportI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.rmi.ConnectException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AccountServiceTests {

    private AccountTranportI accountTranport;
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        accountTranport = mock(AccountTranportI.class);
        accountService = new AccountService(accountTranport);
    }

    @Test
    void getAccountBalance_shouldReturnBalance() throws ConnectException {
        long accountId = 123L;
        BigDecimal expectedBalance = new BigDecimal("500.00");

        when(accountTranport.getAccountBalance(accountId)).thenReturn(expectedBalance);

        BigDecimal actualBalance = accountService.getAccountBalance(accountId);

        assertEquals(expectedBalance, actualBalance);
        verify(accountTranport).getAccountBalance(accountId);
    }

    @Test
    void createAccount_shouldReturnAccountId() throws ConnectException {
        long userId = 456L;
        long expectedAccountId = 789L;

        when(accountTranport.createAccount(userId)).thenReturn(expectedAccountId);

        long actualAccountId = accountService.createAccount(userId);

        assertEquals(expectedAccountId, actualAccountId);
        verify(accountTranport).createAccount(userId);
    }

    @Test
    void deposit_shouldReturnNewBalance() throws ConnectException {
        long userId = 321L;
        BigDecimal amount = new BigDecimal("100.00");
        BigDecimal expectedBalance = new BigDecimal("600.00");

        when(accountTranport.deposit(userId, amount)).thenReturn(expectedBalance);

        BigDecimal actualBalance = accountService.deposit(userId, amount);

        assertEquals(expectedBalance, actualBalance);
        verify(accountTranport).deposit(userId, amount);
    }
}
