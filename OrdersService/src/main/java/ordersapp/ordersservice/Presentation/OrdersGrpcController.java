package ordersapp.ordersservice.Presentation;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import ordersapp.orderservice.proto.OrderServiceGrpc;
import ordersapp.orderservice.proto.OrderServiceProto;
import ordersapp.ordersservice.Domain.Entities.Order;
import ordersapp.ordersservice.Domain.Interfaces.Services.OrdersServiceI;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@GrpcService
@RequiredArgsConstructor
public class OrdersGrpcController extends OrderServiceGrpc.OrderServiceImplBase {
    private final OrdersServiceI ordersService;

    @Override
    public void getOrderStatus(OrderServiceProto.GetOrderStatusRequest request,
                               StreamObserver<OrderServiceProto.GetOrderStatusResponse> responseObserver) {
        var ret = ordersService.getOrderStatusById(request.getOrderId());
        OrderServiceProto.GetOrderStatusResponse response = OrderServiceProto.GetOrderStatusResponse.newBuilder().setOrderStatus(ret).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getOrders(OrderServiceProto.GetOrdersRequest request,
                          StreamObserver<OrderServiceProto.GetOrdersResponse> responseObserver) {
        var ret = ordersService.getOrdersById(request.getUserId());
        List<OrderServiceProto.GetOrdersResponse.Order> orders = new ArrayList<>();
        for (var order: ret) {
            orders.add(OrderServiceProto.GetOrdersResponse.Order.newBuilder().setAmount(order.getAmount().toString()).setOrderId(order.getId()).setPayload(order.getPayload()).setStatus(order.getStatus().name()).setUserId(order.getUserId()).build());
        }

        OrderServiceProto.GetOrdersResponse response = OrderServiceProto.GetOrdersResponse.newBuilder().addAllOrders(orders).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void createOrder(ordersapp.orderservice.proto.OrderServiceProto.CreateOrderRequest request,
                            io.grpc.stub.StreamObserver<ordersapp.orderservice.proto.OrderServiceProto.CreateOrderResponse> responseObserver) {

        var ret = ordersService.createOrder(Order.builder().payload(request.getPayload()).amount(new BigDecimal(request.getAmount())).userId(request.getUserId()).build());
        OrderServiceProto.CreateOrderResponse response = OrderServiceProto.CreateOrderResponse.newBuilder().setOrderId(ret).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
