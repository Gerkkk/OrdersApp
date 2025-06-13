package ordersapp.paymentservice;

import net.devh.boot.grpc.server.service.GrpcService;
import ordersapp.paymentservice.Domain.Interfaces.Services.AccountServiceI;
import ordersapp.paymentservice.Presentation.PaymentGrpcController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import plagiatchecker.accountservice.proto.AccountServiceProto;

import java.math.BigDecimal;

import io.grpc.stub.StreamObserver;

import static org.mockito.Mockito.*;

class PaymentControllerTests {

    @Mock
    private AccountServiceI accountService;

    @Mock
    private StreamObserver<AccountServiceProto.GetAccountBalanceResponse> balanceObserver;

    @Mock
    private StreamObserver<AccountServiceProto.CreateAccountResponse> createObserver;

    @Mock
    private StreamObserver<AccountServiceProto.DepositResponse> depositObserver;

    private PaymentGrpcController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new PaymentGrpcController(accountService);
    }

    @Test
    void getAccountBalance_shouldSendCorrectResponse() {
        when(accountService.getBalance(1L)).thenReturn(new BigDecimal("300.0"));

        AccountServiceProto.GetAccountBalanceRequest request =
                AccountServiceProto.GetAccountBalanceRequest.newBuilder().setAccountId(1L).build();

        controller.getAccountBalance(request, balanceObserver);

        verify(balanceObserver).onNext(
                AccountServiceProto.GetAccountBalanceResponse.newBuilder()
                        .setAmount("300.0")
                        .build()
        );
        verify(balanceObserver).onCompleted();
    }

    @Test
    void createAccount_shouldSendCorrectResponse() {
        when(accountService.createAccount(1L)).thenReturn(123L);

        AccountServiceProto.CreateAccountRequest request =
                AccountServiceProto.CreateAccountRequest.newBuilder().setUserId(1L).build();

        controller.createAccount(request, createObserver);

        verify(createObserver).onNext(
                AccountServiceProto.CreateAccountResponse.newBuilder().setAccountId(123L).build()
        );
        verify(createObserver).onCompleted();
    }

    @Test
    void deposit_shouldSendCorrectResponse() {
        when(accountService.deposit(1L, new BigDecimal("250.0"))).thenReturn(new BigDecimal("550.0"));

        AccountServiceProto.DepositRequest request =
                AccountServiceProto.DepositRequest.newBuilder().setUserId(1L).setAmount("250.0").build();

        controller.deposit(request, depositObserver);

        verify(depositObserver).onNext(
                AccountServiceProto.DepositResponse.newBuilder().setAmount("550.0").build()
        );
        verify(depositObserver).onCompleted();
    }
}
