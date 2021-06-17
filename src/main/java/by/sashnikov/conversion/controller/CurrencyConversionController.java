package by.sashnikov.conversion.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import by.sashnikov.conversion.service.CurrencyConversionService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/currency/conversion")
public class CurrencyConversionController {

    private final CurrencyConversionService currencyConversionService;

    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    private Mono<ConversionResponse> convert(@RequestBody @Valid ConversionRequest conversionRequest) {
        return currencyConversionService.convert(conversionRequest);
    }

}
