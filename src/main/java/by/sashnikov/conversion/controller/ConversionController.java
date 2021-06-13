package by.sashnikov.conversion.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import by.sashnikov.conversion.service.ConversionService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/currency/conversion")
public class ConversionController {

    private final ConversionService conversionService;

    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    private Mono<ConversionResponse> convert(@RequestBody @Valid ConversionRequest conversionRequest) {
        return conversionService.convert(conversionRequest);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ConversionRequest {
        @NotEmpty
        private String from;

        @NotEmpty
        private String to;
        
        @NotNull
        @DecimalMin(value = "0.0", inclusive = false)
        private BigDecimal amount;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ConversionResponse {
        private String from;
        private String to;
        private BigDecimal amount;
        private BigDecimal converted;
    }
}

