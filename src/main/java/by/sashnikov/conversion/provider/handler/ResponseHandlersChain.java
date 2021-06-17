package by.sashnikov.conversion.provider.handler;

import org.springframework.web.reactive.function.client.ClientResponse;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ResponseHandlersChain<T> {

    private final List<? extends ResponseHandler<T>> handlers;

    public Mono<T> handle(ClientResponse clientResponse) {
        Mono<T> result = Mono.empty();

        for (ResponseHandler<T> handler : handlers) {
            Optional<Mono<T>> handle = handler.handle(clientResponse);
            result = handle.orElse(Mono.empty());
        }

        return result.switchIfEmpty(Mono.error(new IllegalStateException("No handler found for response: " + clientResponse)));
    }
}

