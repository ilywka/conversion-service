package by.sashnikov.conversion.provider.handler;

import org.springframework.web.reactive.function.client.ClientResponse;

import java.util.Optional;

import reactor.core.publisher.Mono;

public interface ResponseHandler<T> {
    Optional<Mono<T>> handle(ClientResponse clientResponse);
}
