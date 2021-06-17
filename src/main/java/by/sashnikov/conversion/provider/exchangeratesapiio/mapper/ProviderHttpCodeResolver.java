package by.sashnikov.conversion.provider.exchangeratesapiio.mapper;

import org.springframework.http.HttpStatus;

import java.util.Map;

import lombok.experimental.UtilityClass;

@UtilityClass
class ProviderHttpCodeResolver {

    private static final Map<String, HttpStatus> error2httpCode = Map.ofEntries(
        Map.entry("invalid_base_currency", HttpStatus.BAD_REQUEST),
        Map.entry("invalid_currency_codes", HttpStatus.BAD_REQUEST)
    );

    public static HttpStatus resolve(String errorType) {
        return error2httpCode.getOrDefault(errorType, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
