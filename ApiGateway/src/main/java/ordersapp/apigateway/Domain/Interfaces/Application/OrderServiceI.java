package ordersapp.apigateway.Domain.Interfaces.Application;

import ordersapp.apigateway.Domain.Dto.Requests.CreateOrderRequest;
import ordersapp.apigateway.Domain.Entities.Order;

import java.rmi.ConnectException;
import java.util.List;

public interface OrderServiceI {
    List<Order> getOrdersByUserId(long userId) throws ConnectException;
    String GetOrderStatus(long orderId) throws ConnectException;
    long AddOrder(CreateOrderRequest order) throws ConnectException;
}
