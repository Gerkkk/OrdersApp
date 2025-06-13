package ordersapp.apigateway;

import ordersapp.apigateway.Application.OrderService;
import ordersapp.apigateway.Domain.Dto.Requests.CreateOrderRequest;
import ordersapp.apigateway.Domain.Entities.Order;
import ordersapp.apigateway.Domain.Interfaces.Transport.OrderTransportI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.rmi.ConnectException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OrderServiceTests {

    private OrderTransportI orderTransport;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderTransport = mock(OrderTransportI.class);
        orderService = new OrderService(orderTransport);
    }

    @Test
    void getOrdersByUserId_shouldReturnListOfOrders() throws ConnectException {
        long userId = 1L;
        List<Order> expectedOrders = Arrays.asList(new Order(1, 1, BigDecimal.valueOf(1), "{}", "NEW") , new Order(1, 1, BigDecimal.valueOf(1), "{}", "NEW"));

        when(orderTransport.getOrdersByUserId(userId)).thenReturn(expectedOrders);

        List<Order> actualOrders = orderService.getOrdersByUserId(userId);

        assertEquals(expectedOrders, actualOrders);
        verify(orderTransport).getOrdersByUserId(userId);
    }

    @Test
    void getOrderStatus_shouldReturnStatus() throws ConnectException {
        long orderId = 10L;
        String expectedStatus = "PROCESSING";

        when(orderTransport.GetOrderStatus(orderId)).thenReturn(expectedStatus);

        String actualStatus = orderService.GetOrderStatus(orderId);

        assertEquals(expectedStatus, actualStatus);
        verify(orderTransport).GetOrderStatus(orderId);
    }

    @Test
    void addOrder_shouldReturnOrderId() throws ConnectException {
        CreateOrderRequest request = new CreateOrderRequest();
        long expectedOrderId = 42L;

        when(orderTransport.AddOrder(request)).thenReturn(expectedOrderId);

        long actualOrderId = orderService.AddOrder(request);

        assertEquals(expectedOrderId, actualOrderId);
        verify(orderTransport).AddOrder(request);
    }
}
