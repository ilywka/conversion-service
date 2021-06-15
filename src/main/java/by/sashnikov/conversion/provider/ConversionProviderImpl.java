package by.sashnikov.conversion.provider;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

import by.sashnikov.conversion.exception.CurrencyConversionException;
import by.sashnikov.conversion.model.ConversionRate;
import by.sashnikov.conversion.provider.model.ProviderPairConversionRateErrorResponse;
import by.sashnikov.conversion.provider.model.ProviderPairConversionRateSuccessResponse;
import by.sashnikov.conversion.provider.model.ProviderPairConversionResponse;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ConversionProviderImpl implements ConversionProvider {

    private final WebClient webClient;
    private final ProviderProperties providerProperties;

    public ConversionProviderImpl(WebClient.Builder clientBuilder, ProviderProperties providerProperties) {
        this.providerProperties = providerProperties;
        this.webClient = clientBuilder.clone()
            .baseUrl(providerProperties.getApiUrl())
            .build();
    }

    @Override
    public Mono<ConversionRate> getConversionRate(String from, String to) {
        return callExternalApi(from, to)
            .flatMap(this::handleResponse);
    }

    private Mono<ProviderPairConversionResponse> callExternalApi(String from, String to) {
        Map<String, Object> uriVariables =
            Map.ofEntries(
                Map.entry("from", from),
                Map.entry("to", to),
                Map.entry("apiKey", providerProperties.getApiKey())
            );
        return webClient.get()
            .uri(providerProperties.getPairConversionPath(), uriVariables)
            .retrieve()
            .onStatus(HttpStatus::isError,
                clientResponse -> Mono.error(new CurrencyConversionException(String.format("Currency conversion request error, %s", clientResponse.rawStatusCode()))))
            .bodyToMono(ProviderPairConversionResponse.class);
    }

    private Mono<ConversionRate> handleResponse(ProviderPairConversionResponse response) {
        if (ProviderPairConversionRateSuccessResponse.class.equals(response.getClass())) {
            return Mono.just(handleSuccess(((ProviderPairConversionRateSuccessResponse) response)));
        } else if (ProviderPairConversionRateErrorResponse.class.equals(response.getClass())) {
            log.debug("Received error conversion response {}", response);
            String errorType = ((ProviderPairConversionRateErrorResponse) response).getErrorType();
            HttpStatus httpCode = ProviderErrorCodeResolver.resolve(errorType);
            return Mono.error(new CurrencyConversionException(String.format("Currency conversion error: %s", errorType), httpCode));
        } else {
            return Mono.error(new IllegalArgumentException(String.format("Unknown response type: %s", response.getClass())));
        }
    }

    private ConversionRate handleSuccess(ProviderPairConversionRateSuccessResponse response) {
        return ConversionRate.builder()
            .from(response.getBaseCode())
            .to(response.getTargetCode())
            .rate(response.getConversionRate())
            .build();
    }
}
