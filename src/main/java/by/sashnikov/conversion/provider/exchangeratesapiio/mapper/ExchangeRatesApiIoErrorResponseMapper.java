package by.sashnikov.conversion.provider.exchangeratesapiio.mapper;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import by.sashnikov.conversion.exception.CurrencyConversionException;
import by.sashnikov.conversion.model.ConversionRate;
import by.sashnikov.conversion.provider.exchangeratesapiio.ExchangeRatesApiIoConversionProviderEnabled;
import by.sashnikov.conversion.provider.exchangeratesapiio.model.ExchangeRatesApiIoPairConversionErrorResponse;
import by.sashnikov.conversion.provider.exchangeratesapiio.model.ExchangeRatesApiIoPairConversionErrorResponse.ErrorDescription;
import by.sashnikov.conversion.provider.exchangeratesapiio.model.ExchangeRatesApiIoPairConversionResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@ExchangeRatesApiIoConversionProviderEnabled
public class ExchangeRatesApiIoErrorResponseMapper implements ExchangeRatesApiIoConversionRateResponseMapper {
    @Override
    public Class<ExchangeRatesApiIoPairConversionErrorResponse> supportedResponseType() {
        return ExchangeRatesApiIoPairConversionErrorResponse.class;
    }

    @Override
    public ConversionRate apply(ExchangeRatesApiIoPairConversionResponse response) {
        var errorResponse = supportedResponseType().cast(response);
        log.debug("Received error conversion response {}", errorResponse);
        ErrorDescription error = errorResponse.getError();
        String errorType = error.getInfo();
        HttpStatus httpCode = ProviderHttpCodeResolver.resolve(error.getCode());
        throw new CurrencyConversionException(String.format("Currency conversion error: %s", errorType), httpCode);
    }
}

