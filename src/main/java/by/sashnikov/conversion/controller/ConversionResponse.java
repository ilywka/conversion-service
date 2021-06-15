package by.sashnikov.conversion.controller;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConversionResponse {
    private String from;
    private String to;
    private BigDecimal amount;
    private BigDecimal converted;
}
