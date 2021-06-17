package by.sashnikov.conversion.provider.handler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.client.ClientResponse;

import java.util.List;
import java.util.Optional;

import reactor.core.publisher.Mono;

class ResponseHandlersChainTest {

    @Test
    void testChain() {
        //given
        ResponseHandler<Object> failHandler = clientResponse -> Optional.empty();
        Object testObject = new Object();
        ResponseHandler<Object> successHandler = clientResponse -> Optional.of(Mono.fromSupplier(() -> testObject));
        ResponseHandlersChain<Object> chain = new ResponseHandlersChain<>(List.of(failHandler, successHandler));

        //when
        Object actualResult = chain.handle(Mockito.mock(ClientResponse.class)).block();

        //then
        Assertions.assertSame(actualResult, testObject);
    }
}