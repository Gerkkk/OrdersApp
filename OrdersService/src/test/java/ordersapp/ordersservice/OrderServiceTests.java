package ordersapp.ordersservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import ordersapp.ordersservice.Application.OrdersService;
import ordersapp.ordersservice.Domain.Entities.Order;
import ordersapp.ordersservice.Domain.Enums.OrderStatus;
import ordersapp.ordersservice.Domain.Interfaces.Repositories.OrdersRepositoryI;
import ordersapp.ordersservice.Domain.Interfaces.Repositories.OutboxEventRepositoryI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTests {

    @Mock
    private OrdersRepositoryI ordersRepository;

    @Mock
    private OutboxEventRepositoryI outboxEventRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private OrdersService ordersService;

    private Order order;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        order = Order.builder()
                .id(1L)
                .userId(123L)
                .amount(new BigDecimal("100.00"))
                .payload("payload")
                .build();
    }

    @Test
    void getOrdersById_shouldReturnOrders() {
        when(ordersRepository.getOrdersByUserId(123L)).thenReturn(List.of(order));

        List<Order> result = ordersService.getOrdersById(123L);
        assertEquals(1, result.size());
        verify(ordersRepository).getOrdersByUserId(123L);
    }

    @Test
    void getOrderStatusById_shouldReturnStatus() {
        order.setStatus(OrderStatus.NEW);
        when(ordersRepository.getOrderById(1L)).thenReturn(order);

        String status = ordersService.getOrderStatusById(1L);
        assertEquals("NEW", status);
    }

    @Test
    void createOrder_shouldSaveOrderAndOutbox() throws Exception {
        order.setStatus(null);
        Order savedOrder = Order.builder()
                .id(1L)
                .userId(order.getUserId())
                .amount(order.getAmount())
                .payload(order.getPayload())
                .status(OrderStatus.NEW)
                .build();

        when(ordersRepository.save(any())).thenReturn(savedOrder);
        when(objectMapper.writeValueAsString(any())).thenReturn("{\"some\":\"json\"}");

        Long id = ordersService.createOrder(order);
        assertEquals(1L, id);
        verify(outboxEventRepository).save(any());
    }

    @Test
    void updateOrder_shouldSaveOrder() {
        ordersService.updateOrder(order);
        verify(ordersRepository).save(order);
    }

    @Test
    void getOrderById_shouldReturnOptionalOrder() {
        when(ordersRepository.getOrderById(1L)).thenReturn(order);

        Optional<Order> result = ordersService.getOrderById(1L);
        assertTrue(result.isPresent());
        assertEquals(order, result.get());
    }
}
