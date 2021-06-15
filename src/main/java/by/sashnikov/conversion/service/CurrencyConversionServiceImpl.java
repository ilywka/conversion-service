package by.sashnikov.conversion.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import by.sashnikov.conversion.controller.ConversionRequest;
import by.sashnikov.conversion.controller.ConversionResponse;
import by.sashnikov.conversion.provider.ConversionProvider;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CurrencyConversionServiceImpl implements CurrencyConversionService {

    private final ConversionProvider conversionProvider;
    private final CurrencyConverter currencyConverter;

    @Override
    public Mono<ConversionResponse> convert(ConversionRequest conversionRequest) {
        return conversionProvider.getConversionRate(conversionRequest.getFrom(), conversionRequest.getTo())
            .map(conversionRate -> currencyConverter.convert(conversionRequest.getAmount(), conversionRate.getRate()))
            .map(convertedValue -> createResponse(conversionRequest, convertedValue));
    }

    private ConversionResponse createResponse(ConversionRequest conversionRequest, BigDecimal convertedValue) {
        return ConversionResponse.builder()
            .from(conversionRequest.getFrom())
            .to(conversionRequest.getTo())
            .amount(conversionRequest.getAmount())
            .converted(convertedValue)
            .build();
    }
}
