package by.sashnikov.conversion.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import static by.sashnikov.conversion.util.TestUtil.objectAsJsonString;
import static by.sashnikov.conversion.util.TestUtil.readFileAsObject;
import static by.sashnikov.conversion.util.TestUtil.readFileAsString;

import com.fasterxml.jackson.core.type.TypeReference;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import by.sashnikov.conversion.controller.ConversionController.ConversionRequest;
import by.sashnikov.conversion.controller.ConversionController.ConversionResponse;
import by.sashnikov.conversion.service.ConversionService;
import reactor.core.publisher.Mono;

@WebFluxTest(ConversionController.class)
class ConversionControllerTest {

    private static final String REQUEST_DATA_FILE_PATH = "classpath:by/sashnikov/conversion/controller/conversion-request-test-data.json";
    private static final String RESPONSE_DATA_FILE_PATH = "classpath:by/sashnikov/conversion/controller/conversion-response-test-data.json";
    private static final String REQUEST_VALIDATION_DATA_FILE_PATH = "classpath:by/sashnikov/conversion/controller/conversion-validation-test-data.json";
    private static final String CONVERSION_ENDPOINT_PATH = "/api/v1/currency/conversion";

    @MockBean
    private ConversionService conversionServiceMock;
    @Autowired
    private WebTestClient testClient;

    @Test
    void shouldCallConversionService() {
        //given
        String requestString = readFileAsString(REQUEST_DATA_FILE_PATH);
        String responseString = readFileAsString(RESPONSE_DATA_FILE_PATH);

        ConversionResponse conversionResponse = new ConversionResponse("qwe", "asd", BigDecimal.valueOf(1.11), BigDecimal.valueOf(2.22));
        when(conversionServiceMock.convert(any(ConversionRequest.class))).thenReturn(Mono.just(conversionResponse));

        //when
        ResponseSpec responseSpec = testClient.post()
            .uri(CONVERSION_ENDPOINT_PATH)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(requestString)
            .exchange();

        //then
        responseSpec
            .expectStatus().isOk()
            .expectBody().json(responseString);
    }

    @ParameterizedTest
    @MethodSource("validationTestData")
    void testConversionValidation(ConversionRequest request) {
        //when
        ResponseSpec responseSpec = testClient.post()
            .uri(CONVERSION_ENDPOINT_PATH)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(objectAsJsonString(request))
            .exchange();

        //then
        responseSpec.expectStatus().isBadRequest();
    }

    private static Stream<Arguments> validationTestData() {
        return readFileAsObject(REQUEST_VALIDATION_DATA_FILE_PATH, new TypeReference<List<ConversionRequest>>() {})
            .stream()
            .map(Arguments::of);
    }
}