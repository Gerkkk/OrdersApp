package ordersapp.apigateway.Domain.Dto.Requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DepositRequest {
    @NotNull
    @Min(1)
    BigDecimal amount;
}
