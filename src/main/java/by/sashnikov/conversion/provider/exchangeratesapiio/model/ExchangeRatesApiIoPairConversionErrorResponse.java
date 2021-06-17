package by.sashnikov.conversion.provider.exchangeratesapiio.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonDeserialize
public class ExchangeRatesApiIoPairConversionErrorResponse extends ExchangeRatesApiIoPairConversionResponse {
    private ErrorDescription error;

    @Data
    public static class ErrorDescription {
        private int code;
        private String info;
    }
}

