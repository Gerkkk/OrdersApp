package ordersapp.apigateway.Transport;

import lombok.RequiredArgsConstructor;
import ordersapp.apigateway.Domain.Interfaces.Transport.AccountTranportI;
import org.springframework.stereotype.Service;
import plagiatchecker.accountservice.proto.AccountServiceGrpc;
import plagiatchecker.accountservice.proto.AccountServiceProto;
import java.math.BigDecimal;
import java.rmi.ConnectException;
import java.rmi.server.ServerNotActiveException;

@Service
@RequiredArgsConstructor
public class PaymentClient implements AccountTranportI {
    private final AccountServiceGrpc.AccountServiceBlockingStub stub;

    @Override
    public BigDecimal getAccountBalance(long accountId) throws ConnectException {
        AccountServiceProto.GetAccountBalanceRequest input = AccountServiceProto.GetAccountBalanceRequest.newBuilder().setAccountId(accountId).build();
        AccountServiceProto.GetAccountBalanceResponse res = stub.getAccountBalance(input);

        if (res == null || res.getAmount() == null) {
            throw new ConnectException("Payment service is unavailable");
        }

        return new BigDecimal(res.getAmount());
    }

    @Override
    public long createAccount(long userId) throws ConnectException {
        AccountServiceProto.CreateAccountRequest input = AccountServiceProto.CreateAccountRequest.newBuilder().setUserId(userId).build();
        AccountServiceProto.CreateAccountResponse res = stub.createAccount(input);

        if (res == null) {
            throw new ConnectException("Payment service is unavailable");
        }

        return res.getAccountId();
    }

    @Override
    public BigDecimal deposit(long userId, BigDecimal amount) throws ConnectException {
        AccountServiceProto.DepositRequest input = AccountServiceProto.DepositRequest.newBuilder().setUserId(userId).setAmount(amount.toString()).build();
        AccountServiceProto.DepositResponse res = stub.deposit(input);

        if (res == null) {
            throw new ConnectException("Payment service is unavailable");
        }

        return new BigDecimal(res.getAmount());
    }
}
