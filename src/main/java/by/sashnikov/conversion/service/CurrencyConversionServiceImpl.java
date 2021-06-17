package by.sashnikov.conversion.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

import by.sashnikov.conversion.controller.ConversionRequest;
import by.sashnikov.conversion.controller.ConversionResponse;
import by.sashnikov.conversion.exception.InternalCurrencyConversionException;
import by.sashnikov.conversion.model.ConversionRate;
import by.sashnikov.conversion.provider.ConversionProvider;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CurrencyConversionServiceImpl implements CurrencyConversionService {

    private final List<ConversionProvider> conversionProviders;
    private final CurrencyConverter currencyConverter;

    @Override
    public Mono<ConversionResponse> convert(ConversionRequest conversionRequest) {
        Queue<ConversionProvider> queue = shuffleProviders();
        ConversionProvider provider;
        Mono<ConversionRate> pipeline = queue.poll().getConversionRate(conversionRequest.getFrom(), conversionRequest.getTo());
        while ((provider = queue.poll()) != null) {
            ConversionProvider conversionProvider = provider;
            pipeline = pipeline.onErrorResume(InternalCurrencyConversionException.class, e -> conversionProvider.getConversionRate(conversionRequest.getFrom(), conversionRequest.getTo()));
        }
        return pipeline.map(conversionRate -> currencyConverter.convert(conversionRequest.getAmount(), conversionRate.getRate()))
            .map(convertedValue -> createResponse(conversionRequest, convertedValue));
    }

    private Queue<ConversionProvider> shuffleProviders() {
        List<ConversionProvider> providers = new ArrayList<>(this.conversionProviders);
        Collections.shuffle(providers);
        Queue<ConversionProvider> queue = new ArrayDeque<>(providers);
        return queue;
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
