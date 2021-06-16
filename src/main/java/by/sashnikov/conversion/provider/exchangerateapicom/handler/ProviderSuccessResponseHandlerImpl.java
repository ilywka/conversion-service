package by.sashnikov.conversion.provider.exchangerateapicom.handler;

import org.springframework.stereotype.Component;

import by.sashnikov.conversion.model.ConversionRate;
import by.sashnikov.conversion.provider.exchangerateapicom.ExchangeRateApiComConversionProviderEnabled;
import by.sashnikov.conversion.provider.exchangerateapicom.model.ProviderPairConversionRateSuccessResponse;
import by.sashnikov.conversion.provider.exchangerateapicom.model.ProviderPairConversionResponse;

@Component
@ExchangeRateApiComConversionProviderEnabled
public class ProviderSuccessResponseHandlerImpl implements ProviderResponseHandler<ProviderPairConversionRateSuccessResponse> {

    @Override
    public Class<? extends ProviderPairConversionResponse> responseType() {
        return ProviderPairConversionRateSuccessResponse.class;
    }

    @Override
    public ConversionRate handle(ProviderPairConversionRateSuccessResponse response) {
        return ConversionRate.builder()
            .from(response.getBaseCode())
            .to(response.getTargetCode())
            .rate(response.getConversionRate())
            .build();
    }
}

