package by.sashnikov.conversion.provider.exchangerateapicom;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@ConditionalOnProperty("conversion.provider.exchangerate-api-com.enabled")
public @interface ExchangeRateApiComConversionProviderEnabled {
}
