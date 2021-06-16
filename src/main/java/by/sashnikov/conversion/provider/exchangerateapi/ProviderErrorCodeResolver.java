package by.sashnikov.conversion.provider.exchangerateapi;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class ProviderErrorCodeResolver {

    private static final Map<String, HttpStatus> error2httpCode = Map.ofEntries(
        Map.entry("unsupported-code", HttpStatus.BAD_REQUEST)
    );

    public static HttpStatus resolve(String errorType) {
        return error2httpCode.getOrDefault(errorType, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
