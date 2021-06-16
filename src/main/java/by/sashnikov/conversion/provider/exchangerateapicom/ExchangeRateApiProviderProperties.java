package by.sashnikov.conversion.provider.exchangerateapicom;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import by.sashnikov.conversion.provider.ProviderProperties;

@Configuration
@ConfigurationProperties("conversion.provider.exchangerate-api-com")
@ExchangeRateApiComConversionProviderEnabled
public class ExchangeRateApiProviderProperties extends ProviderProperties {
}

