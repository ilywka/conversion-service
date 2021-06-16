package by.sashnikov.conversion.provider.exchangerateapicom;

import org.springframework.http.HttpStatus;

import java.util.Map;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ProviderHttpCodeResolver {

    private static final Map<String, HttpStatus> error2httpCode = Map.ofEntries(
        Map.entry("unsupported-code", HttpStatus.BAD_REQUEST)
    );

    public static HttpStatus resolve(String errorType) {
        return error2httpCode.getOrDefault(errorType, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
