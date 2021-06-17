package by.sashnikov.conversion.provider.exchangerateapicom.mapper;

import org.springframework.stereotype.Component;

import by.sashnikov.conversion.model.ConversionRate;
import by.sashnikov.conversion.provider.exchangerateapicom.ExchangeRateApiComConversionProviderEnabled;
import by.sashnikov.conversion.provider.exchangerateapicom.model.ExchangeRateApiComPairConversionRateSuccessResponse;
import by.sashnikov.conversion.provider.exchangerateapicom.model.ExchangeRateApiComPairConversionResponse;

@Component
@ExchangeRateApiComConversionProviderEnabled
public class ExchangeRateApiComSuccessResponseMapper implements ExchangeRateApiComConversionRateResponseMapper {

    @Override
    public Class<ExchangeRateApiComPairConversionRateSuccessResponse> supportedResponseType() {
        return ExchangeRateApiComPairConversionRateSuccessResponse.class;
    }

    @Override
    public ConversionRate apply(ExchangeRateApiComPairConversionResponse response) {
        var successResponse = supportedResponseType().cast(response);
        return ConversionRate.builder()
            .from(successResponse.getBaseCode())
            .to(successResponse.getTargetCode())
            .rate(successResponse.getConversionRate())
            .build();
    }
}

