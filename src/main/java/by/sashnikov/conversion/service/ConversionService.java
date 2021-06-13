package by.sashnikov.conversion.service;

import by.sashnikov.conversion.controller.ConversionController.ConversionRequest;
import by.sashnikov.conversion.controller.ConversionController.ConversionResponse;
import reactor.core.publisher.Mono;

public interface ConversionService {
    Mono<ConversionResponse> convert(ConversionRequest conversionRequest);
}

