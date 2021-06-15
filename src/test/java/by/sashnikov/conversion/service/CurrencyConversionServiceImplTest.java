package by.sashnikov.conversion.service;

import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import by.sashnikov.conversion.controller.ConversionRequest;
import by.sashnikov.conversion.controller.ConversionResponse;
import by.sashnikov.conversion.model.ConversionRate;
import by.sashnikov.conversion.provider.ConversionProvider;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class CurrencyConversionServiceImplTest {

    @Mock
    private ConversionProvider conversionProvider;
    @Mock
    private CurrencyConverter currencyConverter;

    @InjectMocks
    private CurrencyConversionServiceImpl service;

    @ParameterizedTest
    @CsvSource({"from,to,1.0,1.0"})
    void testConvert(String from, String to, BigDecimal amount, BigDecimal rate) {
        //given
        ConversionRequest request = new ConversionRequest(from, to, amount);
        ConversionRate conversionRate = ConversionRate.builder().from(from).to(to).rate(rate).build();

        when(conversionProvider.getConversionRate(from, to)).thenReturn(Mono.just(conversionRate));
        when(currencyConverter.convert(amount, rate)).thenReturn(amount);
        
        //when
        ConversionResponse actualResponse = service.convert(request).block();

        //then
        ConversionResponse expectedResponse = new ConversionResponse(from, to, amount, amount);
        Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
    }
}
