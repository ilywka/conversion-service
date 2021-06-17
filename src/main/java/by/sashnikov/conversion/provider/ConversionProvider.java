package by.sashnikov.conversion.provider;

import by.sashnikov.conversion.model.ConversionRate;
import reactor.core.publisher.Mono;

public interface ConversionProvider {
    Mono<ConversionRate> getConversionRate(String from, String to);
}
