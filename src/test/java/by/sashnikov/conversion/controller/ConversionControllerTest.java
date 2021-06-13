package by.sashnikov.conversion.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import java.io.IOException;
import java.math.BigDecimal;

import by.sashnikov.conversion.controller.ConversionController.ConversionRequest;
import by.sashnikov.conversion.controller.ConversionController.ConversionResponse;
import by.sashnikov.conversion.service.ConversionService;
import by.sashnikov.conversion.util.TestUtil;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class ConversionControllerTest {

    private static final String REQUEST_DATA_FILE_PATH = "classpath:by/sashnikov/conversion/controller/conversion-request-test-data.json";
    private static final String RESPONSE_DATA_FILE_PATH = "classpath:by/sashnikov/conversion/controller/conversion-response-test-data.json";

    @Mock
    private ConversionService conversionServiceMock;
    private WebTestClient testClient;

    @BeforeEach
    void setUp() {
        testClient = WebTestClient.bindToController(new ConversionController(conversionServiceMock))
            .configureClient()
            .baseUrl("/api/v1/currency/conversion")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }

    @Test
    void shouldCallConversionService() throws IOException {
        //given
        String requestString = TestUtil.readFileAsString(REQUEST_DATA_FILE_PATH);
        String responseString = TestUtil.readFileAsString(RESPONSE_DATA_FILE_PATH);

        ConversionResponse conversionResponse = new ConversionResponse("qwe", "asd", BigDecimal.valueOf(1.11), BigDecimal.valueOf(2.22));
        when(conversionServiceMock.convert(any(ConversionRequest.class))).thenReturn(Mono.just(conversionResponse));

        //when
        ResponseSpec responseSpec = testClient.post().bodyValue(requestString).exchange();

        //then
        responseSpec.expectStatus().isOk()
            .expectBody().json(responseString);
    }
}