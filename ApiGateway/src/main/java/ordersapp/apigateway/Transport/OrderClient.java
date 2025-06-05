package ordersapp.apigateway.Transport;

import lombok.RequiredArgsConstructor;
import ordersapp.apigateway.Domain.Dto.Requests.CreateOrderRequest;
import ordersapp.apigateway.Domain.Entities.Order;
import ordersapp.apigateway.Domain.Enums.OrderStatus;
import ordersapp.apigateway.Domain.Interfaces.Application.OrderServiceI;
import ordersapp.apigateway.Domain.Interfaces.Transport.OrderTransportI;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import plagiatchecker.orderservice.proto.OrderServiceGrpc;
import plagiatchecker.orderservice.proto.OrderServiceProto;

import java.math.BigDecimal;
import java.rmi.ConnectException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderClient implements OrderTransportI {
    private final OrderServiceGrpc.OrderServiceBlockingStub stub;

    @Override
    public List<Order> getOrdersByUserId(long userId) throws ConnectException {
        OrderServiceProto.GetOrdersRequest input = OrderServiceProto.GetOrdersRequest.newBuilder().setUserId(userId).build();
        OrderServiceProto.GetOrdersResponse res = stub.getOrders(input);

        if (res == null) {
            throw new ConnectException("Order service is not available");
        }

        ArrayList<Order> orders = new ArrayList<>();
        for (OrderServiceProto.GetOrdersResponse.Order item: res.getOrdersList()) {
            BigDecimal am = new BigDecimal(item.getAmount());
            Order ord = Order.builder().userId(item.getUserId()).amount(am).payload(item.getPayload()).id(item.getOrderId()).status(OrderStatus.valueOf(item.getStatus())).build();
            orders.add(ord);
        }

        return orders;
    }

    @Override
    public String GetOrderStatus(long orderId) throws ConnectException {
        OrderServiceProto.GetOrderStatusRequest input = OrderServiceProto.GetOrderStatusRequest.newBuilder().setOrderId(orderId).build();
        OrderServiceProto.GetOrderStatusResponse res = stub.getOrderStatus(input);

        if (res == null) {
            throw new ConnectException("Order service is not available");
        }

        return res.getOrderStatus();
    }

    @Override
    public long AddOrder(CreateOrderRequest order) throws ConnectException {
        OrderServiceProto.CreateOrderRequest input = OrderServiceProto.CreateOrderRequest.newBuilder().setUserId(order.getUserId()).setAmount(order.getAmount().toString()).setPayload(order.getPayload()).build();
        OrderServiceProto.CreateOrderResponse res = stub.createOrder(input);

        if (res == null) {
            throw new ConnectException("Order service is not available");
        }

        return res.getOrderId();
    }
}
