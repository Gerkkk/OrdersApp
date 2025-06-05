package ordersapp.apigateway.Domain.Dto.Requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateOrderRequest {
    @NotNull @Min(1)
    long userId;
    @NotNull @Min(0)
    BigDecimal amount;
    @NotNull @Size(min = 2)
    String payload;
    public void validate(ObjectMapper mapper) throws JsonProcessingException {
        mapper.readTree(this.payload);
    }
}
