package by.sashnikov.conversion.provider.exchangerateapicom.handler;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import by.sashnikov.conversion.exception.CurrencyConversionException;
import by.sashnikov.conversion.model.ConversionRate;
import by.sashnikov.conversion.provider.exchangerateapicom.ExchangeRateApiComConversionProviderEnabled;
import by.sashnikov.conversion.provider.exchangerateapicom.ProviderHttpCodeResolver;
import by.sashnikov.conversion.provider.exchangerateapicom.model.ProviderPairConversionRateErrorResponse;
import by.sashnikov.conversion.provider.exchangerateapicom.model.ProviderPairConversionResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@ExchangeRateApiComConversionProviderEnabled
public class ProviderErrorResponseHandlerImpl implements ProviderResponseHandler<ProviderPairConversionRateErrorResponse> {

    @Override
    public Class<? extends ProviderPairConversionResponse> responseType() {
        return ProviderPairConversionRateErrorResponse.class;
    }

    @Override
    public ConversionRate handle(ProviderPairConversionRateErrorResponse response) {
        log.debug("Received error conversion response {}", response);
        String errorType = response.getErrorType();
        HttpStatus httpCode = ProviderHttpCodeResolver.resolve(errorType);
        throw new CurrencyConversionException(String.format("Currency conversion error: %s", errorType), httpCode);
    }
}

