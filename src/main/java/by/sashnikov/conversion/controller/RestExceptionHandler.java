package by.sashnikov.conversion.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import by.sashnikov.conversion.exception.CurrencyConversionException;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler
    public Mono<?> conversionExceptionHandler(CurrencyConversionException exception) {
        return Mono.error(new ResponseStatusException(exception.getHttpStatus(), exception.getMessage()));
    }
}
