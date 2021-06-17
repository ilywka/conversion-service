package by.sashnikov.conversion.provider.exchangeratesapiio.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.math.BigDecimal;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonDeserialize
public class ExchangeRatesApiIoPairConversionSuccessResponse extends ExchangeRatesApiIoPairConversionResponse {
    private Boolean success;
    private String base;
    private Map<String, BigDecimal> rates;
}

