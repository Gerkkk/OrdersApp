package ordersapp.apigateway.Domain.Dto.Responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ordersapp.apigateway.Domain.Enums.OrderStatus;

@Getter
@AllArgsConstructor
public class GetOrderStatusResponse {
    private OrderStatus orderStatus;

    public GetOrderStatusResponse (String status) {
        System.out.println("LOL: " + status + " " + status.toUpperCase());
        orderStatus = OrderStatus.valueOf(status.toUpperCase());
    }
}
