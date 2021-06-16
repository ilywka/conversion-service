package by.sashnikov.conversion.provider.exchangerateapicom.handler;

import by.sashnikov.conversion.model.ConversionRate;
import by.sashnikov.conversion.provider.exchangerateapicom.model.ProviderPairConversionResponse;

public interface ProviderResponseHandler<T extends ProviderPairConversionResponse> {

    Class<? extends ProviderPairConversionResponse> responseType();

    ConversionRate handle(T response);
}

