package ordersapp.apigateway.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ordersapp.apigateway.Domain.Dto.Requests.CreateOrderRequest;
import ordersapp.apigateway.Domain.Dto.Requests.DepositRequest;
import ordersapp.apigateway.Domain.Dto.Responses.GetOrderStatusResponse;
import ordersapp.apigateway.Domain.Dto.Responses.GetOrdersListResponse;
import ordersapp.apigateway.Domain.Interfaces.Application.AccountsServiceI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.rmi.ConnectException;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Счета", description = "Запросы для создания платежного счета, пополнения счета, получения информации о счетах")
public class AccountController {
    private final AccountsServiceI accountService;

    @GetMapping("balance/{id}")
    @Operation(summary = "Получить баланс на счете пользователя по id пользователя")
    public ResponseEntity<BigDecimal> getAccountBalance(@PathVariable("id") long id) {
        try {
            BigDecimal ret = accountService.getAccountBalance(id);
            return ResponseEntity.ok(ret);
        } catch (ConnectException e) {
            return ResponseEntity.status(503).build();
        } catch (Exception e) {
            log.error("Get Balance exception: {}",e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/{userId}")
    @Operation(summary = "Создать счет для пользователя, если он еще не существует. Вернет id нового счета.")
    public ResponseEntity<Long> createAccount(@PathVariable long userId) {
        try {
            Long ret = accountService.createAccount(userId);
            return ResponseEntity.ok(ret);
        } catch (ConnectException e) {
            return ResponseEntity.status(503).build();
        } catch (Exception e) {
            log.error("Create account exception: {}",e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("deposit/{userId}")
    @Operation(summary = "Положить сумму на счет пользователя с заданным id. Вернет новый баланс.")
    public ResponseEntity<BigDecimal> deposit(@PathVariable long userId, @RequestBody @Valid DepositRequest request) {
        try {
            BigDecimal ret = accountService.deposit(userId, request.getAmount());
            return ResponseEntity.ok(ret);
        } catch (ConnectException e) {
            return ResponseEntity.status(503).build();
        } catch (Exception e) {
            log.error("Deposit exception: {}",e.getMessage());
            return ResponseEntity.status(500).build();
        }

    }
}
