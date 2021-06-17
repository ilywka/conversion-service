package by.sashnikov.conversion.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

public class UserInputCurrencyConversionException extends CurrencyConversionException {

    public UserInputCurrencyConversionException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
