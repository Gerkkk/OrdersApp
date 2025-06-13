package ordersapp.apigateway;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import ordersapp.apigateway.Transport.PaymentClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import plagiatchecker.accountservice.proto.AccountServiceGrpc;
import plagiatchecker.accountservice.proto.AccountServiceProto;

import java.math.BigDecimal;
import java.rmi.ConnectException;

class PaymentClientTests {

    @Mock
    private AccountServiceGrpc.AccountServiceBlockingStub stub;

    private PaymentClient paymentClient;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        paymentClient = new PaymentClient(stub);
    }

    @Test
    void getAccountBalance_returnsBalance() throws Exception {
        long accountId = 10L;

        AccountServiceProto.GetAccountBalanceResponse grpcResponse = AccountServiceProto.GetAccountBalanceResponse.newBuilder()
                .setAmount("2000.50")
                .build();

        when(stub.getAccountBalance(any())).thenReturn(grpcResponse);

        BigDecimal balance = paymentClient.getAccountBalance(accountId);

        assertEquals(new BigDecimal("2000.50"), balance);
    }

    @Test
    void getAccountBalance_throwsConnectException_whenResponseNull() {
        when(stub.getAccountBalance(any())).thenReturn(null);

        assertThrows(ConnectException.class, () -> paymentClient.getAccountBalance(1L));
    }

    @Test
    void createAccount_returnsAccountId() throws Exception {
        long userId = 123L;

        AccountServiceProto.CreateAccountResponse grpcResponse = AccountServiceProto.CreateAccountResponse.newBuilder()
                .setAccountId(999L)
                .build();

        when(stub.createAccount(any())).thenReturn(grpcResponse);

        long accountId = paymentClient.createAccount(userId);

        assertEquals(999L, accountId);
    }

    @Test
    void createAccount_throwsConnectException_whenResponseNull() {
        when(stub.createAccount(any())).thenReturn(null);

        assertThrows(ConnectException.class, () -> paymentClient.createAccount(1L));
    }

    @Test
    void deposit_returnsNewAmount() throws Exception {
        long userId = 1L;
        BigDecimal amount = new BigDecimal("150.0");

        AccountServiceProto.DepositResponse grpcResponse = AccountServiceProto.DepositResponse.newBuilder()
                .setAmount("175.0")
                .build();

        when(stub.deposit(any())).thenReturn(grpcResponse);

        BigDecimal newAmount = paymentClient.deposit(userId, amount);

        assertEquals(new BigDecimal("175.0"), newAmount);
    }

    @Test
    void deposit_throwsConnectException_whenResponseNull() {
        when(stub.deposit(any())).thenReturn(null);

        assertThrows(ConnectException.class, () -> paymentClient.deposit(1L, new BigDecimal("100")));
    }
}
