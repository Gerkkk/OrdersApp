package ordersapp.apigateway.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ordersapp.apigateway.Domain.Dto.Requests.CreateOrderRequest;
import ordersapp.apigateway.Domain.Dto.Responses.GetOrderStatusResponse;
import ordersapp.apigateway.Domain.Dto.Responses.GetOrdersListResponse;
import ordersapp.apigateway.Domain.Interfaces.Application.OrderServiceI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.rmi.ConnectException;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Заказы", description = "запросы для создания заказа, получения информации о заказах")
public class OrdersController {
    private final OrderServiceI orderService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("status/{id}")
    @Operation(summary = "Получить статус заказа по его id")
    public ResponseEntity<GetOrderStatusResponse> getOrderStatus(@PathVariable("id") long id) {
        try {
            GetOrderStatusResponse response = new GetOrderStatusResponse(orderService.GetOrderStatus(id));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ConnectException e) {
            return ResponseEntity.status(503).build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/list/{id}")
    @Operation(summary = "Получить по id пользователя список его заказов")
    public ResponseEntity<GetOrdersListResponse> getOrdersList(@PathVariable("id") long id) {
        try {
            GetOrdersListResponse response = new GetOrdersListResponse(orderService.getOrdersByUserId(id));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ConnectException e) {
            return ResponseEntity.status(503).build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/")
    @Operation(summary = "Создать заказ с заданными параметрами")
    public ResponseEntity<Long> createOrder(@RequestBody @Valid CreateOrderRequest request) {
        try {
            request.validate(this.objectMapper);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        try {
            Long response = orderService.AddOrder(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ConnectException e) {
            return ResponseEntity.status(503).build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }
}