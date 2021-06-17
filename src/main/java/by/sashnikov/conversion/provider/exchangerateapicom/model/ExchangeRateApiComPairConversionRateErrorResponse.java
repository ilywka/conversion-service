package by.sashnikov.conversion.provider.exchangerateapicom.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonDeserialize
@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
public class ExchangeRateApiComPairConversionRateErrorResponse extends ExchangeRateApiComPairConversionResponse {

    private String errorType;
}
