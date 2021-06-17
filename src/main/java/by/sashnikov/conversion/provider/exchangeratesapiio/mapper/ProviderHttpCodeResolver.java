package by.sashnikov.conversion.provider.exchangeratesapiio.mapper;

import org.springframework.http.HttpStatus;

import java.util.Map;

import lombok.experimental.UtilityClass;

@UtilityClass
class ProviderHttpCodeResolver {

    private static final Map<Integer, HttpStatus> error2httpCode = Map.ofEntries(
        Map.entry(201, HttpStatus.BAD_REQUEST),
        Map.entry(202, HttpStatus.BAD_REQUEST)
    );

    public static HttpStatus resolve(int errorType) {
        return error2httpCode.getOrDefault(errorType, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
