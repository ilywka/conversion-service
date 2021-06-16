package by.sashnikov.conversion.provider.exchangerateapi.responsehandler;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import by.sashnikov.conversion.exception.CurrencyConversionException;
import by.sashnikov.conversion.model.ConversionRate;
import by.sashnikov.conversion.provider.exchangerateapi.ProviderErrorCodeResolver;
import by.sashnikov.conversion.provider.exchangerateapi.model.ProviderPairConversionRateErrorResponse;
import by.sashnikov.conversion.provider.exchangerateapi.model.ProviderPairConversionResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ProviderErrorResponseHandlerImpl implements ProviderResponseHandler<ProviderPairConversionRateErrorResponse> {
    @Override
    public boolean canHandle(Class<? extends ProviderPairConversionResponse> responseClass) {
        return ProviderPairConversionRateErrorResponse.class.equals(responseClass);
    }

    @Override
    public ConversionRate handle(ProviderPairConversionRateErrorResponse response) {
        log.debug("Received error conversion response {}", response);
        String errorType = response.getErrorType();
        HttpStatus httpCode = ProviderErrorCodeResolver.resolve(errorType);
        throw new CurrencyConversionException(String.format("Currency conversion error: %s", errorType), httpCode);
    }
}

