package ordersapp.apigateway.Configs;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import plagiatchecker.accountservice.proto.AccountServiceGrpc;
import plagiatchecker.orderservice.proto.OrderServiceGrpc;

@Configuration
public class GrpcConfig {

    @GrpcClient("orders-service")
    private OrderServiceGrpc.OrderServiceBlockingStub fileServiceStub;

    @GrpcClient("payment-service")
    private AccountServiceGrpc.AccountServiceBlockingStub accountServiceStub;

    @Bean
    public AccountServiceGrpc.AccountServiceBlockingStub accountServiceStub() {
        return accountServiceStub;
    }

    @Bean
    public OrderServiceGrpc.OrderServiceBlockingStub fileServiceStub() {
        return fileServiceStub;
    }
}