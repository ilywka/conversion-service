package by.sashnikov.conversion.provider.exchangerateapi.responsehandler;

import by.sashnikov.conversion.model.ConversionRate;
import by.sashnikov.conversion.provider.exchangerateapi.model.ProviderPairConversionResponse;

public interface ProviderResponseHandler<T extends ProviderPairConversionResponse> {

    boolean canHandle(Class<? extends ProviderPairConversionResponse> aClass);

    ConversionRate handle(T response);
}

