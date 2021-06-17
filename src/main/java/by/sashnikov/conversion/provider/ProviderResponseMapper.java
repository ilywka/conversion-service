package by.sashnikov.conversion.provider;

import java.util.function.Function;

public interface ProviderResponseMapper<T, R> extends Function<T, R> {
    Class<? extends T> supportedResponseType();
}
