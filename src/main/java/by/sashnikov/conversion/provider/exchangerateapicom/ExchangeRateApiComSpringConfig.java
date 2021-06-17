package by.sashnikov.conversion.provider.exchangerateapicom;

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
import by.sashnikov.conversion.provider.exchangerateapicom.mapper.ExchangeRateApiComConversionRateResponseMapper;
import by.sashnikov.conversion.provider.handler.ConversionRateResponseHandler;
import by.sashnikov.conversion.provider.handler.GenericResponseHandler;
import by.sashnikov.conversion.provider.handler.ResponseHandler;
import by.sashnikov.conversion.provider.handler.ResponseHandlersChain;

@Configuration
@ExchangeRateApiComConversionProviderEnabled
public class ExchangeRateApiComSpringConfig {

    @Bean("exchangeRateApiComSuccessResponseHandler")
    @Order
    public ResponseHandler<ConversionRate> successHandler(List<ExchangeRateApiComConversionRateResponseMapper> mappers) {
        return new ConversionRateResponseHandler<>(mappers);
    }

    @Bean
    @ConfigurationProperties(prefix = "conversion.provider.exchangerate-api-com")
    public ProviderProperties exchangeRateApiComProviderProperties() {
        return new ProviderProperties();
    }

    @Bean
    public ResponseHandlersChain<ConversionRate> exchangeRateApiComResponseHandlersChain(
        List<GenericResponseHandler<ConversionRate>> genericResponseHandlers,
        ResponseHandler<ConversionRate> exchangeRateApiComSuccessResponseHandler
    ) {
        List<ResponseHandler<ConversionRate>> handlers = new ArrayList<>(genericResponseHandlers);
        handlers.add(exchangeRateApiComSuccessResponseHandler);
        return new ResponseHandlersChain<>(handlers);
    }

    @Bean
    public ConversionProvider conversionProvider(
        Builder clientBuilder,
        ProviderProperties exchangeRateApiComProviderProperties,
        ResponseHandlersChain<ConversionRate> exchangeRateApiComResponseHandlersChain
    ) {
        return new ConversionProviderImpl(clientBuilder, exchangeRateApiComProviderProperties, exchangeRateApiComResponseHandlersChain);
    }
}

