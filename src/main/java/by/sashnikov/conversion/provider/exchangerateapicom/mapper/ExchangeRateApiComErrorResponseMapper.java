package by.sashnikov.conversion.provider.exchangerateapicom.mapper;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import by.sashnikov.conversion.exception.CurrencyConversionException;
import by.sashnikov.conversion.model.ConversionRate;
import by.sashnikov.conversion.provider.exchangerateapicom.model.ExchangeRateApiComPairConversionRateErrorResponse;
import by.sashnikov.conversion.provider.exchangerateapicom.model.ExchangeRateApiComPairConversionResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ExchangeRateApiComErrorResponseMapper implements ExchangeRateApiComConversionRateResponseMapper {
    @Override
    public Class<ExchangeRateApiComPairConversionRateErrorResponse> supportedResponseType() {
        return ExchangeRateApiComPairConversionRateErrorResponse.class;
    }

    @Override
    public ConversionRate apply(ExchangeRateApiComPairConversionResponse response) {
        log.debug("Received error conversion response {}", response);
        var errorResponse = supportedResponseType().cast(response);
        String errorType = errorResponse.getErrorType();
        HttpStatus httpCode = ProviderHttpCodeResolver.resolve(errorType);
        throw new CurrencyConversionException(String.format("Currency conversion error: %s", errorType), httpCode);
    }
}

