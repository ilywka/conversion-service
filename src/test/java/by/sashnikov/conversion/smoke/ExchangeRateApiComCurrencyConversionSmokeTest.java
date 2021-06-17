package by.sashnikov.conversion.smoke;

import static by.sashnikov.conversion.util.TestUtil.readFileAsString;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;
import java.util.stream.Stream;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ExchangeRateApiComCurrencyConversionSmokeTest {

    private static final String CONVERSION_REQUEST_FILE = "classpath:by/sashnikov/conversion/smoke/exchangerateapicom/conversion-request-test-data.json";
    private static final String CONVERSION_INCORRECT_CODE_REQUEST_FILE =
        "classpath:by/sashnikov/conversion/smoke/exchangerateapicom/conversion-incorrect-code-request-test-data.json";
    private static final String CONVERSION_QUOTA_REACHED_REQUEST_FILE =
        "classpath:by/sashnikov/conversion/smoke/exchangerateapicom/conversion-quota-reached-request-test-data.json";
    private static final String CONVERSION_RATE_PROVIDER_SUCCESS_RESPONSE_FILE =
        "classpath:by/sashnikov/conversion/smoke/exchangerateapicom/conversion-rate-provider-success-response-test-data.json";
    private static final String CONVERSION_RATE_PROVIDER_INCORRECT_CODE_RESPONSE_FILE =
        "classpath:by/sashnikov/conversion/smoke/exchangerateapicom/conversion-rate-provider-incorrect-code-response-test-data.json";
    private static final String CONVERSION_RATE_PROVIDER_QUOTA_REACHED_RESPONSE_FILE =
        "classpath:by/sashnikov/conversion/smoke/exchangerateapicom/conversion-rate-provider-quota-reached-response-test-data.json";
    private static final String CONVERSION_EXPECTED_RESPONSE_FILE =
        "classpath:by/sashnikov/conversion/smoke/exchangerateapicom/conversion-expected-response-test-data.json";
    private static final String PROVIDER_CONVERSION_REQUEST_PATH = "/{api-key}/pair/{from}/{to}";
    private static final String API_KEY = "1q2w3e4r";

    private static MockWebServer mockWebServer;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeAll
    static void beforeAll() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    static void afterAll() throws IOException {
        mockWebServer.shutdown();
    }

    @DynamicPropertySource
    static void registerMockServerProperties(DynamicPropertyRegistry registry) {
        registry.add("conversion.provider.exchangerate-api-com.enabled", () -> true);
        registry.add("conversion.provider.exchangerate-api-com.apiUrl", () -> mockWebServer.url("").toString());
        registry.add("conversion.provider.exchangerate-api-com.apiKey", () -> API_KEY);
    }

    @BeforeEach
    void setUp() {
        mockWebServer.setDispatcher(new Dispatcher() {
            @Override
            public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
                String conversionCorrectRequestPath = PROVIDER_CONVERSION_REQUEST_PATH
                    .replace("{api-key}", API_KEY)
                    .replace("{from}", "USD")
                    .replace("{to}", "EUR");
                String conversionInvalidRequestPath = conversionCorrectRequestPath.replace("USD", "QWE");
                String conversionQuotaReachedPath = conversionCorrectRequestPath.replace("USD", "AED");
                if (conversionCorrectRequestPath.equals(request.getPath())) {
                    return new MockResponse().setResponseCode(HttpStatus.OK.value())
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(readFileAsString(CONVERSION_RATE_PROVIDER_SUCCESS_RESPONSE_FILE));
                } else if (conversionInvalidRequestPath.equals(request.getPath())) {
                    return new MockResponse().setResponseCode(HttpStatus.OK.value())
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(readFileAsString(CONVERSION_RATE_PROVIDER_INCORRECT_CODE_RESPONSE_FILE));
                } else if (conversionQuotaReachedPath.equals(request.getPath())) {
                    return new MockResponse().setResponseCode(HttpStatus.OK.value())
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(readFileAsString(CONVERSION_RATE_PROVIDER_QUOTA_REACHED_RESPONSE_FILE));
                }
                return new MockResponse().setResponseCode(HttpStatus.NOT_FOUND.value());
            }
        });
    }

    @Test
    void correctConversionTest() {
        webTestClient.post().uri("/api/v1/currency/conversion")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(readFileAsString(CONVERSION_REQUEST_FILE))
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
            .expectBody().json(readFileAsString(CONVERSION_EXPECTED_RESPONSE_FILE));
    }

    @ParameterizedTest
    @MethodSource("invalidConversionTestData")
    void invalidConversionTest(String requestDataFile, HttpStatus expectedHttpStatus) {
        webTestClient.post().uri("/api/v1/currency/conversion")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(readFileAsString(requestDataFile))
            .exchange()
            .expectStatus().isEqualTo(expectedHttpStatus);
    }

    private static Stream<Arguments> invalidConversionTestData() {
        return Stream.of(
            Arguments.of(CONVERSION_INCORRECT_CODE_REQUEST_FILE, HttpStatus.BAD_REQUEST),
            Arguments.of(CONVERSION_QUOTA_REACHED_REQUEST_FILE, HttpStatus.INTERNAL_SERVER_ERROR)
        );
    }
}
