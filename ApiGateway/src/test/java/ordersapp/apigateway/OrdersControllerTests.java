package ordersapp.apigateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import ordersapp.apigateway.Controllers.OrdersController;
import ordersapp.apigateway.Domain.Dto.Requests.CreateOrderRequest;
import ordersapp.apigateway.Domain.Dto.Responses.GetOrderStatusResponse;
import ordersapp.apigateway.Domain.Dto.Responses.GetOrdersListResponse;
import ordersapp.apigateway.Domain.Entities.Order;
import ordersapp.apigateway.Domain.Interfaces.Application.OrderServiceI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.rmi.ConnectException;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class OrdersControllerTests {

    private MockMvc mockMvc;
    private OrderServiceI orderService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        orderService = mock(OrderServiceI.class);
        OrdersController controller = new OrdersController(orderService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getOrderStatus_shouldReturnStatus() throws Exception {
        when(orderService.GetOrderStatus(1L)).thenReturn("NEW");

        mockMvc.perform(get("/api/orders/status/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderStatus").value("NEW"));

        verify(orderService).GetOrderStatus(1L);
    }

    @Test
    void getOrderStatus_shouldReturn503OnConnectException() throws Exception {
        when(orderService.GetOrderStatus(anyLong())).thenThrow(new ConnectException("Connection error"));



        mockMvc.perform(get("/api/orders/status/1"))
                .andExpect(status().isServiceUnavailable());

        verify(orderService).GetOrderStatus(1L);
    }

    @Test
    void getOrdersList_shouldReturnOrders() throws Exception {
        Order order = new Order(1, 1, BigDecimal.valueOf(1), "{}", "NEW");
        when(orderService.getOrdersByUserId(1L)).thenReturn(Collections.singletonList(order));

        mockMvc.perform(get("/api/orders/list/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orders").isArray());

        verify(orderService).getOrdersByUserId(1L);
    }

    @Test
    void createOrder_shouldReturnOrderId() throws Exception {
        CreateOrderRequest request = new CreateOrderRequest();
        request.setUserId(1L);
        request.setAmount(new BigDecimal("100.00"));
        request.setPayload("{}");

        when(orderService.AddOrder(any(CreateOrderRequest.class))).thenReturn(10L);

        mockMvc.perform(post("/api/orders/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("10"));

        verify(orderService).AddOrder(any(CreateOrderRequest.class));
    }

    @Test
    void createOrder_shouldReturnBadRequestOnInvalidData() throws Exception {
        String invalidJson = "{}";

        mockMvc.perform(post("/api/orders/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }
}
