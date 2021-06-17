package by.sashnikov.conversion.exception;

import org.springframework.http.HttpStatus;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InternalCurrencyConversionException extends CurrencyConversionException {

    public InternalCurrencyConversionException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
