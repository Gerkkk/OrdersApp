package ordersapp.apigateway;
import ordersapp.apigateway.Controllers.AccountController;
import ordersapp.apigateway.Domain.Dto.Requests.DepositRequest;
import ordersapp.apigateway.Domain.Interfaces.Application.AccountsServiceI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.rmi.ConnectException;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
        import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AccountControllerTests {

    private MockMvc mockMvc;
    private AccountsServiceI accountService;

    @BeforeEach
    void setUp() {
        accountService = mock(AccountsServiceI.class);
        AccountController controller = new AccountController(accountService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getAccountBalance_shouldReturnBalance() throws Exception {
        when(accountService.getAccountBalance(1L)).thenReturn(new BigDecimal("123.45"));

        mockMvc.perform(get("/api/accounts/balance/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("123.45"));

        verify(accountService).getAccountBalance(1L);
    }

    @Test
    void createAccount_shouldReturnAccountId() throws Exception {
        when(accountService.createAccount(1L)).thenReturn(100L);

        mockMvc.perform(post("/api/accounts/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("100"));

        verify(accountService).createAccount(1L);
    }

    @Test
    void deposit_shouldReturnNewBalance() throws Exception {
        DepositRequest request = new DepositRequest();
        request.setAmount(new BigDecimal("200"));

        when(accountService.deposit(1L, new BigDecimal("200"))).thenReturn(new BigDecimal("300"));

        mockMvc.perform(post("/api/accounts/deposit/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":200}"))
                .andExpect(status().isOk())
                .andExpect(content().string("300"));

        verify(accountService).deposit(1L, new BigDecimal("200"));
    }

    @Test
    void getAccountBalance_shouldReturn503OnConnectException() throws Exception {
        when(accountService.getAccountBalance(anyLong())).thenThrow(new ConnectException("Connection error"));

        mockMvc.perform(get("/api/accounts/balance/1"))
                .andExpect(status().isServiceUnavailable());

        verify(accountService).getAccountBalance(1L);
    }
}
