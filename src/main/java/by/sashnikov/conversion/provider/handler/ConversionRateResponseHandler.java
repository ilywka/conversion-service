package by.sashnikov.conversion.provider.handler;

import org.apache.commons.lang3.reflect.TypeUtils;
import org.springframework.web.reactive.function.client.ClientResponse;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import by.sashnikov.conversion.model.ConversionRate;
import by.sashnikov.conversion.provider.ProviderResponseMapper;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;

/**
 * @param <T> success response body type
 */
@Slf4j
public class ConversionRateResponseHandler<T> implements ResponseHandler<ConversionRate> {

    private final Map<Class<? extends T>, ProviderResponseMapper<T, ConversionRate>> responseMappers;
    private final Class<T> requestBaseType;

    public ConversionRateResponseHandler(List<? extends ProviderResponseMapper<T, ConversionRate>> mappers) {
        responseMappers = mappers.stream().collect(Collectors.toMap(ProviderResponseMapper::supportedResponseType, Function.identity()));
        requestBaseType = getFunctionBaseType(mappers.stream().findAny().get());
    }

    private Class<T> getFunctionBaseType(ProviderResponseMapper<T, ConversionRate> mapper) {
        return (Class<T>) TypeUtils.getTypeArguments(mapper.getClass(), ProviderResponseMapper.class)
            .get(ProviderResponseMapper.class.getTypeParameters()[0]);
    }

    @Override
    public Optional<Mono<ConversionRate>> handle(ClientResponse clientResponse) {
        return Optional.of(clientResponse)
            .filter(this::isNotServerError)
            .map(clientResponse1 -> clientResponse1.bodyToMono(requestBaseType))
            .map(rMono -> rMono.handle(this::mapResponseObject));
    }

    private void mapResponseObject(T response, SynchronousSink<ConversionRate> sink) {
        ProviderResponseMapper<T, ConversionRate> mapper = responseMappers.get(response.getClass());
        if (mapper != null) {
            sink.next(mapper.apply(response));
        }
    }

    private boolean isNotServerError(ClientResponse clientResponse) {
        return !clientResponse.statusCode().is5xxServerError();
    }
}
