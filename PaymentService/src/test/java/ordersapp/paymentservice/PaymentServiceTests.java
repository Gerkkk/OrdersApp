package ordersapp.paymentservice;

import ordersapp.paymentservice.Application.PaymentService;
import ordersapp.paymentservice.Domain.Entities.Account;
import ordersapp.paymentservice.Domain.Interfaces.Repositories.AccountRepositoryI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceTests {

    @Mock
    private AccountRepositoryI accountRepository;

    private PaymentService paymentService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        paymentService = new PaymentService(accountRepository);
    }

    @Test
    void getBalance_shouldReturnBalance_whenAccountExists() {
        Account account = Account.builder().userId(1L).balance(new BigDecimal("500.0")).build();
        when(accountRepository.findByUserId(1L)).thenReturn(account);

        BigDecimal balance = paymentService.getBalance(1L);
        assertEquals(new BigDecimal("500.0"), balance);
    }

    @Test
    void getBalance_shouldReturnMinusOne_whenAccountNotFound() {
        when(accountRepository.findByUserId(1L)).thenReturn(null);

        BigDecimal balance = paymentService.getBalance(1L);
        assertEquals(new BigDecimal("-1"), balance);
    }

    @Test
    void createAccount_shouldReturnNewId_whenAccountNotExists() {
        when(accountRepository.findByUserId(1L)).thenReturn(null);
        Account saved = Account.builder().id(100L).userId(1L).balance(BigDecimal.ZERO).build();
        when(accountRepository.save(any())).thenReturn(saved);

        Long result = paymentService.createAccount(1L);
        assertEquals(100L, result);
    }

    @Test
    void createAccount_shouldReturnMinusOne_whenAccountAlreadyExists() {
        Account existing = Account.builder().id(50L).userId(1L).build();
        when(accountRepository.findByUserId(1L)).thenReturn(existing);

        Long result = paymentService.createAccount(1L);
        assertEquals(-1L, result);
    }

    @Test
    void deposit_shouldIncreaseBalance_whenAccountExists() {
        Account account = Account.builder().id(1L).userId(1L).balance(new BigDecimal("100")).build();
        Account updated = Account.builder().id(1L).userId(1L).balance(new BigDecimal("150")).build();

        when(accountRepository.findByUserId(1L)).thenReturn(account);
        when(accountRepository.save(any())).thenReturn(updated);

        BigDecimal result = paymentService.deposit(1L, new BigDecimal("50"));
        assertEquals(new BigDecimal("150"), result);
    }

    @Test
    void deposit_shouldReturnMinusOne_whenAccountNotFound() {
        when(accountRepository.findByUserId(1L)).thenReturn(null);

        BigDecimal result = paymentService.deposit(1L, new BigDecimal("50"));
        assertEquals(new BigDecimal("-1"), result);
    }
}
