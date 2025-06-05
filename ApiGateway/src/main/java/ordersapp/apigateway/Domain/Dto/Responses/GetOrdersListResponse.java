package ordersapp.apigateway.Domain.Dto.Responses;

import ordersapp.apigateway.Domain.Entities.Order;

import java.util.List;

public record GetOrdersListResponse (List<Order> orders) {}
