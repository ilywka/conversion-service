package by.sashnikov.conversion.provider.exchangeratesapiio.mapper;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map.Entry;

import by.sashnikov.conversion.model.ConversionRate;
import by.sashnikov.conversion.provider.exchangeratesapiio.ExchangeRatesApiIoConversionProviderEnabled;
import by.sashnikov.conversion.provider.exchangeratesapiio.model.ExchangeRatesApiIoPairConversionResponse;
import by.sashnikov.conversion.provider.exchangeratesapiio.model.ExchangeRatesApiIoPairConversionSuccessResponse;

@Component
@ExchangeRatesApiIoConversionProviderEnabled
public class ExchangeRatesApiIoSuccessResponseMapper implements ExchangeRatesApiIoConversionRateResponseMapper {
    @Override
    public Class<ExchangeRatesApiIoPairConversionSuccessResponse> supportedResponseType() {
        return ExchangeRatesApiIoPairConversionSuccessResponse.class;
    }

    @Override
    public ConversionRate apply(ExchangeRatesApiIoPairConversionResponse response) {
        var successResponse = supportedResponseType().cast(response);
        Entry<String, BigDecimal> conversionRate = successResponse.getRates().entrySet().stream().findAny().get();
        return ConversionRate.builder()
            .from(successResponse.getBase())
            .to(conversionRate.getKey())
            .rate(conversionRate.getValue())
            .build();
    }
}

