package ordersapp.ordersservice;

import io.grpc.stub.StreamObserver;
import ordersapp.orderservice.proto.OrderServiceProto;
import ordersapp.ordersservice.Domain.Entities.Order;
import ordersapp.ordersservice.Domain.Enums.OrderStatus;
import ordersapp.ordersservice.Domain.Interfaces.Services.OrdersServiceI;
import ordersapp.ordersservice.Presentation.OrdersGrpcController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;

class OrderControllerTests {

    @Mock
    private OrdersServiceI ordersService;

    @Mock
    private StreamObserver<OrderServiceProto.GetOrderStatusResponse> statusObserver;

    @Mock
    private StreamObserver<OrderServiceProto.GetOrdersResponse> ordersObserver;

    @Mock
    private StreamObserver<OrderServiceProto.CreateOrderResponse> createObserver;

    private OrdersGrpcController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new OrdersGrpcController(ordersService);
    }

    @Test
    void getOrderStatus_shouldRespondWithStatus() {
        when(ordersService.getOrderStatusById(1L)).thenReturn("PAID");

        var request = OrderServiceProto.GetOrderStatusRequest.newBuilder().setOrderId(1L).build();
        controller.getOrderStatus(request, statusObserver);

        verify(statusObserver).onNext(
                OrderServiceProto.GetOrderStatusResponse.newBuilder().setOrderStatus("PAID").build()
        );
        verify(statusObserver).onCompleted();
    }

    @Test
    void getOrders_shouldReturnOrderList() {
        Order order = Order.builder()
                .id(1L)
                .userId(123L)
                .amount(new BigDecimal("100.0"))
                .payload("test")
                .status(OrderStatus.NEW)
                .build();

        when(ordersService.getOrdersById(123L)).thenReturn(List.of(order));

        var request = OrderServiceProto.GetOrdersRequest.newBuilder().setUserId(123L).build();
        controller.getOrders(request, ordersObserver);

        var expectedOrder = OrderServiceProto.GetOrdersResponse.Order.newBuilder()
                .setOrderId(1L)
                .setUserId(123L)
                .setAmount("100.0")
                .setPayload("test")
                .setStatus("NEW")
                .build();

        verify(ordersObserver).onNext(
                OrderServiceProto.GetOrdersResponse.newBuilder().addOrders(expectedOrder).build()
        );
        verify(ordersObserver).onCompleted();
    }

    @Test
    void createOrder_shouldCallServiceAndReturnId() {
        when(ordersService.createOrder(any())).thenReturn(999L);

        var request = OrderServiceProto.CreateOrderRequest.newBuilder()
                .setUserId(123L)
                .setAmount("200.0")
                .setPayload("payload")
                .build();

        controller.createOrder(request, createObserver);

        verify(createObserver).onNext(
                OrderServiceProto.CreateOrderResponse.newBuilder().setOrderId(999L).build()
        );
        verify(createObserver).onCompleted();
    }
}
