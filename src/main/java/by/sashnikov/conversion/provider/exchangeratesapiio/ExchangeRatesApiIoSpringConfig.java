package by.sashnikov.conversion.provider.exchangeratesapiio;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import java.util.ArrayList;
import java.util.List;

import by.sashnikov.conversion.model.ConversionRate;
import by.sashnikov.conversion.provider.ConversionProvider;
import by.sashnikov.conversion.provider.ConversionProviderImpl;
import by.sashnikov.conversion.provider.ProviderProperties;
import by.sashnikov.conversion.provider.exchangeratesapiio.mapper.ExchangeRatesApiIoConversionRateResponseMapper;
import by.sashnikov.conversion.provider.handler.ConversionRateSuccessResponseHandler;
import by.sashnikov.conversion.provider.handler.GenericResponseHandler;
import by.sashnikov.conversion.provider.handler.ResponseHandler;
import by.sashnikov.conversion.provider.handler.ResponseHandlersChain;

@Configuration
@ExchangeRatesApiIoConversionProviderEnabled
public class ExchangeRatesApiIoSpringConfig {

    @Bean("exchangeRatesApiIoSuccessResponseHandler")
    @Order
    public ResponseHandler<ConversionRate> successHandler(List<ExchangeRatesApiIoConversionRateResponseMapper> mappers) {
        return new ConversionRateSuccessResponseHandler<>(mappers);
    }

    @Bean
    @ConfigurationProperties(prefix = "conversion.provider.exchangerates-api-io")
    public ProviderProperties exchangeRatesApiIoProviderProperties() {
        return new ProviderProperties();
    }

    @Bean
    public ResponseHandlersChain<ConversionRate> exchangeRateApiIoResponseHandlersChain(
        List<GenericResponseHandler<ConversionRate>> genericResponseHandlers,
        ResponseHandler<ConversionRate> exchangeRatesApiIoSuccessResponseHandler
    ) {
        List<ResponseHandler<ConversionRate>> handlers = new ArrayList<>(genericResponseHandlers);
        handlers.add(exchangeRatesApiIoSuccessResponseHandler);
        return new ResponseHandlersChain<>(handlers);
    }

    @Bean
    public ConversionProvider conversionProvider(
        Builder clientBuilder,
        ProviderProperties exchangeRatesApiIoProviderProperties,
        ResponseHandlersChain<ConversionRate> exchangeRateApiIoResponseHandlersChain
    ) {
        return new ConversionProviderImpl(clientBuilder, exchangeRatesApiIoProviderProperties, exchangeRateApiIoResponseHandlersChain);
    }
}

