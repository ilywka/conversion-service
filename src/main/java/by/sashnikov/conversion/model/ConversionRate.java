package by.sashnikov.conversion.model;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConversionRate {
    private String from;
    private String to;
    private BigDecimal rate;
}
