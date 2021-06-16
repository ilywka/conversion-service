package by.sashnikov.conversion.provider.exchangerateapi.responsehandler;

import org.springframework.stereotype.Component;

import by.sashnikov.conversion.model.ConversionRate;
import by.sashnikov.conversion.provider.exchangerateapi.model.ProviderPairConversionRateSuccessResponse;
import by.sashnikov.conversion.provider.exchangerateapi.model.ProviderPairConversionResponse;

@Component
public class ProviderSuccessResponseHandlerImpl implements ProviderResponseHandler<ProviderPairConversionRateSuccessResponse> {
    @Override
    public boolean canHandle(Class<? extends ProviderPairConversionResponse> responseClass) {
        return ProviderPairConversionRateSuccessResponse.class.equals(responseClass);
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

