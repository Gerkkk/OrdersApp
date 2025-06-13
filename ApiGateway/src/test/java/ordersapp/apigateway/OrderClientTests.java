package ordersapp.apigateway;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import ordersapp.apigateway.Domain.Dto.Requests.CreateOrderRequest;
import ordersapp.apigateway.Domain.Entities.Order;
import ordersapp.apigateway.Domain.Enums.OrderStatus;
import ordersapp.apigateway.Transport.OrderClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import plagiatchecker.orderservice.proto.OrderServiceGrpc;
import plagiatchecker.orderservice.proto.OrderServiceProto;

import java.math.BigDecimal;
import java.rmi.ConnectException;
import java.util.List;

class OrderClientTests {

    @Mock
    private OrderServiceGrpc.OrderServiceBlockingStub stub;

    private OrderClient orderClient;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        orderClient = new OrderClient(stub);
    }

    @Test
    void getOrdersByUserId_returnsOrders() throws Exception {
        long userId = 1L;

        OrderServiceProto.GetOrdersResponse.Order grpcOrder = OrderServiceProto.GetOrdersResponse.Order.newBuilder()
                .setOrderId(100L)
                .setUserId(userId)
                .setAmount("99.99")
                .setPayload("data")
                .setStatus("NEW")
                .build();

        OrderServiceProto.GetOrdersResponse grpcResponse = OrderServiceProto.GetOrdersResponse.newBuilder()
                .addOrders(grpcOrder)
                .build();

        when(stub.getOrders(any())).thenReturn(grpcResponse);

        List<Order> orders = orderClient.getOrdersByUserId(userId);

        assertEquals(1, orders.size());
        Order order = orders.get(0);
        assertEquals(100L, order.getId());
        assertEquals(userId, order.getUserId());
        assertEquals(new BigDecimal("99.99"), order.getAmount());
        assertEquals("data", order.getPayload());
        assertEquals(OrderStatus.NEW, order.getStatus());
    }

    @Test
    void getOrdersByUserId_throwsConnectException_whenNullResponse() {
        when(stub.getOrders(any())).thenReturn(null);

        assertThrows(ConnectException.class, () -> orderClient.getOrdersByUserId(1L));
    }

    @Test
    void getOrderStatus_returnsStatus() throws Exception {
        long orderId = 123L;
        OrderServiceProto.GetOrderStatusResponse grpcResponse = OrderServiceProto.GetOrderStatusResponse.newBuilder()
                .setOrderStatus("COMPLETED")
                .build();

        when(stub.getOrderStatus(any())).thenReturn(grpcResponse);

        String status = orderClient.GetOrderStatus(orderId);

        assertEquals("COMPLETED", status);
    }

    @Test
    void getOrderStatus_throwsConnectException_whenNullResponse() {
        when(stub.getOrderStatus(any())).thenReturn(null);

        assertThrows(ConnectException.class, () -> orderClient.GetOrderStatus(123L));
    }

    @Test
    void addOrder_returnsOrderId() throws Exception {
        CreateOrderRequest req = new CreateOrderRequest();
        req.setUserId(5L);
        req.setAmount(new BigDecimal("150.0"));
        req.setPayload("payload");

        OrderServiceProto.CreateOrderResponse grpcResponse = OrderServiceProto.CreateOrderResponse.newBuilder()
                .setOrderId(555L)
                .build();

        when(stub.createOrder(any())).thenReturn(grpcResponse);

        long orderId = orderClient.AddOrder(req);

        assertEquals(555L, orderId);
    }

    @Test
    void addOrder_throwsConnectException_whenNullResponse() {
        CreateOrderRequest req = new CreateOrderRequest();
        req.setUserId(5L);
        req.setAmount(new BigDecimal("150.0"));
        req.setPayload("payload");

        when(stub.createOrder(any())).thenReturn(null);

        assertThrows(ConnectException.class, () -> orderClient.AddOrder(req));
    }
}
