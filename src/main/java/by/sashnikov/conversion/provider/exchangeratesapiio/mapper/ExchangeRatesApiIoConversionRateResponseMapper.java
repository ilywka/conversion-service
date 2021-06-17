package by.sashnikov.conversion.provider.exchangeratesapiio.mapper;

import by.sashnikov.conversion.model.ConversionRate;
import by.sashnikov.conversion.provider.ProviderResponseMapper;
import by.sashnikov.conversion.provider.exchangeratesapiio.ExchangeRatesApiIoConversionProviderEnabled;
import by.sashnikov.conversion.provider.exchangeratesapiio.model.ExchangeRatesApiIoPairConversionResponse;

@ExchangeRatesApiIoConversionProviderEnabled
public interface ExchangeRatesApiIoConversionRateResponseMapper
    extends ProviderResponseMapper<ExchangeRatesApiIoPairConversionResponse, ConversionRate> {
}

