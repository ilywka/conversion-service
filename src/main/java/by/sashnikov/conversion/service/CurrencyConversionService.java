package by.sashnikov.conversion.service;

import by.sashnikov.conversion.controller.ConversionRequest;
import by.sashnikov.conversion.controller.ConversionResponse;
import reactor.core.publisher.Mono;

public interface CurrencyConversionService {
    Mono<ConversionResponse> convert(ConversionRequest conversionRequest);
}
