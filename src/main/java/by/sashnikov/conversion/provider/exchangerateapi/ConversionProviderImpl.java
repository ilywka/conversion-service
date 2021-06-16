package by.sashnikov.conversion.provider;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import java.util.List;
import java.util.Map;

import by.sashnikov.conversion.exception.CurrencyConversionException;
import by.sashnikov.conversion.model.ConversionRate;
import by.sashnikov.conversion.provider.exchangerateapi.model.ProviderPairConversionResponse;
import by.sashnikov.conversion.provider.exchangerateapi.responsehandler.ProviderResponseHandler;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ConversionProviderImpl implements ConversionProvider {

    private final WebClient webClient;
    private final ProviderProperties providerProperties;
    private final List<ProviderResponseHandler> responseHandlers;

    public ConversionProviderImpl(
        Builder clientBuilder,
        ProviderProperties providerProperties,
        List<ProviderResponseHandler> responseHandlers
    ) {
        this.providerProperties = providerProperties;
        this.responseHandlers = responseHandlers;
        this.webClient = clientBuilder.clone()
            .baseUrl(providerProperties.getApiUrl())
            .build();
    }

    @Override
    public Mono<ConversionRate> getConversionRate(String from, String to) {
        return callExternalApi(from, to)
            .map(this::handleResponse);
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

    @SuppressWarnings("unchecked")
    private ConversionRate handleResponse(ProviderPairConversionResponse response) {
        for (ProviderResponseHandler responseHandler : responseHandlers) {
            if (responseHandler.canHandle(response.getClass())) {
                return responseHandler.handle(response);
            }
        }
        throw new IllegalArgumentException(String.format("No handler found for response type: %s", response.getClass()));
    }
}
