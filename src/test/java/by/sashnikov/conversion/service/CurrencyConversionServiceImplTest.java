package by.sashnikov.conversion.service;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;

import by.sashnikov.conversion.controller.ConversionRequest;
import by.sashnikov.conversion.controller.ConversionResponse;
import by.sashnikov.conversion.exception.InternalCurrencyConversionException;
import by.sashnikov.conversion.model.ConversionRate;
import by.sashnikov.conversion.provider.ConversionProvider;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class CurrencyConversionServiceImplTest {

    @Mock
    private ConversionProvider conversionProvider;
    @Mock
    private ConversionProvider failingConversionProvider;
    @Mock
    private CurrencyConverter currencyConverter;
    @Spy
    private ArrayList<ConversionProvider> conversionProviders;
    @InjectMocks
    private CurrencyConversionServiceImpl service;

    @BeforeEach
    void setUp() {
        conversionProviders.add(conversionProvider);
        conversionProviders.add(failingConversionProvider);
    }

    @ParameterizedTest
    @CsvSource({"from,to,1.0,1.0"})
    void testConvert(String from, String to, BigDecimal amount, BigDecimal rate) {
        //given
        ConversionRequest request = new ConversionRequest(from, to, amount);
        ConversionRate conversionRate = ConversionRate.builder().from(from).to(to).rate(rate).build();

        lenient().when(failingConversionProvider.getConversionRate(from, to)).thenReturn(Mono.error(new InternalCurrencyConversionException()));
        when(conversionProvider.getConversionRate(from, to)).thenReturn(Mono.just(conversionRate));
        when(currencyConverter.convert(amount, rate)).thenReturn(amount);
        
        //when
        ConversionResponse actualResponse = service.convert(request).block();

        //then
        ConversionResponse expectedResponse = new ConversionResponse(from, to, amount, amount);
        Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
    }
}
