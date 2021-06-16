package by.sashnikov.conversion.provider.exchangerateapicom.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonDeserialize
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProviderPairConversionRateSuccessResponse extends ProviderPairConversionResponse {
    private String baseCode;
    private String targetCode;
    private BigDecimal conversionRate;
}
