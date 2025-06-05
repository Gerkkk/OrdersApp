package ordersapp.apigateway.Application;

import lombok.RequiredArgsConstructor;
import ordersapp.apigateway.Domain.Dto.Requests.CreateOrderRequest;
import ordersapp.apigateway.Domain.Entities.Order;
import ordersapp.apigateway.Domain.Interfaces.Application.OrderServiceI;
import ordersapp.apigateway.Domain.Interfaces.Transport.OrderTransportI;
import org.springframework.stereotype.Component;

import java.rmi.ConnectException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderService implements OrderServiceI {
    private final OrderTransportI orderTransport;

    @Override
    public List<Order> getOrdersByUserId(long userId) throws ConnectException {
        //There in future we can add checking if the user has rights to get the list of orders. By calling authorisation service, for example.
        return orderTransport.getOrdersByUserId(userId);
    }

    @Override
    public String GetOrderStatus(long orderId) throws ConnectException {
        //There in future we can add checking if the user has rights to get the status of this order. By calling authorisation service, for example.
        return orderTransport.GetOrderStatus(orderId);
    }

    @Override
    public long AddOrder(CreateOrderRequest order) throws ConnectException {
        //There in future we can add checking if the user has rights to create an order for the user that is mentioned in request. By calling authorisation service, for example.
        return orderTransport.AddOrder(order);
    }
}
