package by.sashnikov.conversion.service;

import java.math.BigDecimal;

public interface CurrencyConverter {
    BigDecimal convert(BigDecimal amount, BigDecimal rate);
}
