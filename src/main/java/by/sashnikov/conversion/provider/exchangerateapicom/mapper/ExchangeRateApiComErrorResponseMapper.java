package by.sashnikov.conversion.provider.exchangerateapicom.mapper;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import by.sashnikov.conversion.exception.InternalCurrencyConversionException;
import by.sashnikov.conversion.exception.UserInputCurrencyConversionException;
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
        if (httpCode.is4xxClientError()) {
            throw new UserInputCurrencyConversionException(String.format("Currency conversion error: %s", errorType));
        } else {
            throw new InternalCurrencyConversionException(String.format("Currency conversion error: %s", errorType));
        }
    }
}

