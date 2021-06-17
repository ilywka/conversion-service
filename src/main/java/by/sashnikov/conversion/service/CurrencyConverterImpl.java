package by.sashnikov.conversion.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CurrencyConverterImpl implements CurrencyConverter {
    @Override
    public BigDecimal convert(BigDecimal amount, BigDecimal rate) {
        return amount.multiply(rate);
    }
}
