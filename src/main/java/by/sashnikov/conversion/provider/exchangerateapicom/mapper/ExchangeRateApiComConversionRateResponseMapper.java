package by.sashnikov.conversion.provider.exchangerateapicom.mapper;

import by.sashnikov.conversion.model.ConversionRate;
import by.sashnikov.conversion.provider.ProviderResponseMapper;
import by.sashnikov.conversion.provider.exchangerateapicom.ExchangeRateApiComConversionProviderEnabled;
import by.sashnikov.conversion.provider.exchangerateapicom.model.ExchangeRateApiComPairConversionResponse;

@ExchangeRateApiComConversionProviderEnabled
public interface ExchangeRateApiComConversionRateResponseMapper
    extends ProviderResponseMapper<ExchangeRateApiComPairConversionResponse, ConversionRate> {
}

