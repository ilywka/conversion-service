package by.sashnikov.conversion.controller;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversionRequest {
    @NotEmpty
    @Pattern(regexp = "[A-Z]{3}")
    private String from;

    @NotEmpty
    @Pattern(regexp = "[A-Z]{3}")
    private String to;

    @NotNull
    @Positive
    private BigDecimal amount;
}
