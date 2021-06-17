package by.sashnikov.conversion.provider;

import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;

import java.util.Map;

import by.sashnikov.conversion.model.ConversionRate;
import by.sashnikov.conversion.provider.handler.ResponseHandlersChain;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class ConversionProviderImpl implements ConversionProvider {

    private final WebClient webClient;
    private final ProviderProperties providerProperties;
    private final ResponseHandlersChain<ConversionRate> responseHandlersChain;

    public ConversionProviderImpl(
        Builder clientBuilder,
        ProviderProperties providerProperties,
        ResponseHandlersChain<ConversionRate> responseHandlersChain) {
        this.providerProperties = providerProperties;
        this.responseHandlersChain = responseHandlersChain;
        this.webClient = buildWebClient(clientBuilder, providerProperties);
    }

    private WebClient buildWebClient(Builder clientBuilder, ProviderProperties providerProperties) {
        return clientBuilder.clone()
            .baseUrl(providerProperties.getApiUrl())
            .build();
    }

    @Override
    public Mono<ConversionRate> getConversionRate(String from, String to) {
        RequestHeadersSpec<?> defaultRequestSpec = createRequestHeadersSpec(from, to);
        log.info("Getting conversion rate using url: {}", providerProperties.getApiUrl());
        return customizeRequestSpec(defaultRequestSpec)
            .exchangeToMono(this::handleClientResponse);
    }

    private Mono<ConversionRate> handleClientResponse(ClientResponse clientResponse) {
        return responseHandlersChain.handle(clientResponse);
    }

    protected RequestHeadersSpec<?> customizeRequestSpec(RequestHeadersSpec<?> requestHeadersSpec) {
        return requestHeadersSpec;
    }

    private RequestHeadersSpec<?> createRequestHeadersSpec(String from, String to) {
        Map<String, Object> uriVariables =
            Map.ofEntries(
                Map.entry("from", from),
                Map.entry("to", to),
                Map.entry("api-key", providerProperties.getApiKey())
            );
        return webClient.get()
            .uri(providerProperties.getConversionRatePath(), uriVariables);
    }
}