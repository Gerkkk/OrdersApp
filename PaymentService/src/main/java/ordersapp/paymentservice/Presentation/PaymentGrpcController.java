package ordersapp.paymentservice.Presentation;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import ordersapp.paymentservice.Domain.Interfaces.Services.AccountServiceI;
import plagiatchecker.accountservice.proto.AccountServiceGrpc;
import plagiatchecker.accountservice.proto.AccountServiceProto;

import java.math.BigDecimal;

@GrpcService
@RequiredArgsConstructor
public class PaymentGrpcController extends AccountServiceGrpc.AccountServiceImplBase {
    private final AccountServiceI accountService;

    @Override
    public void getAccountBalance(AccountServiceProto.GetAccountBalanceRequest request,
                                  StreamObserver<AccountServiceProto.GetAccountBalanceResponse> responseObserver) {
        var ret = accountService.getBalance(request.getAccountId());
        AccountServiceProto.GetAccountBalanceResponse response = AccountServiceProto.GetAccountBalanceResponse.newBuilder().setAmount(ret.toString()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void createAccount(AccountServiceProto.CreateAccountRequest request,
                              StreamObserver<AccountServiceProto.CreateAccountResponse> responseObserver) {
        var ret = accountService.createAccount(request.getUserId());
        AccountServiceProto.CreateAccountResponse response = AccountServiceProto.CreateAccountResponse.newBuilder().setAccountId(ret).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void deposit(AccountServiceProto.DepositRequest request,
                        StreamObserver<AccountServiceProto.DepositResponse> responseObserver) {
        var ret = accountService.deposit(request.getUserId(), new BigDecimal(request.getAmount()));
        AccountServiceProto.DepositResponse response = AccountServiceProto.DepositResponse.newBuilder().setAmount(ret.toString()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
