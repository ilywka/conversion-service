package by.sashnikov.conversion.provider.handler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;

import java.util.Optional;

import by.sashnikov.conversion.exception.InternalCurrencyConversionException;
import reactor.core.publisher.Mono;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@SuppressWarnings("rawtypes")
public class ConversionRateServerErrorResponseHandler implements GenericResponseHandler {

    @Override
    public Optional<Mono> handle(ClientResponse clientResponse) {
        return Optional.of(clientResponse)
            .map(ClientResponse::statusCode)
            .filter(HttpStatus::isError)
            .map(this::statusToException);
    }

    private Mono statusToException(HttpStatus httpStatus) {
        String message = String.format("Currency conversion request error, %s", httpStatus.value());
        return Mono.error(new InternalCurrencyConversionException(message));
    }
}

