package by.sashnikov.conversion.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CurrencyConversionException extends RuntimeException {

    private final HttpStatus httpStatus;

    public CurrencyConversionException(String message) {
        super(message);
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public CurrencyConversionException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
