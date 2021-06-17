package by.sashnikov.conversion.provider.handler;

import org.springframework.web.reactive.function.client.ClientResponse;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
public class ResponseHandlersChain<T> {

    private final List<? extends ResponseHandler<T>> handlers;

    public Mono<T> handle(ClientResponse clientResponse) {
        Mono<T> result = Mono.empty();

        for (ResponseHandler<T> handler : handlers) {
            Optional<Mono<T>> handle = handler.handle(clientResponse);
            handle.ifPresent(tMono -> log.info("Found conversion response handler: {}", handler));
            result = handle.orElse(Mono.empty());
        }

        return result.switchIfEmpty(Mono.error(new IllegalStateException("No handler found for response: " + clientResponse)));
    }
}

