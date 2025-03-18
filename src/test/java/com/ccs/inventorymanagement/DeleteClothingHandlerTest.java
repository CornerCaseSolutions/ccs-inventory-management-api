package com.ccs.inventorymanagement;

import com.ccs.inventorymanagement.config.RouteConfig;
import com.ccs.inventorymanagement.route.DeleteClothingHandler;
import com.ccs.inventorymanagement.service.ClothingService;
import io.r2dbc.spi.R2dbcBadGrammarException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;
import java.util.function.Consumer;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteClothingHandlerTest {

    @Mock
    ClothingService clothingService;

    @InjectMocks
    DeleteClothingHandler deleteClothingHandler;

    @Test
    @DisplayName("DEL Delete Clothing Handler: handle() happy path")
    public void shouldDeleteClothingHandler() {
        //Given
        UUID id = UUID.randomUUID();
        ServerRequest serverRequest = mock(ServerRequest.class);

        //When
        when(serverRequest.pathVariable(RouteConfig.ID_VARIABLE))
                .thenReturn(id.toString());
        when(clothingService.delete(id))
                .thenReturn(Mono.empty());

        //Then
        deleteClothingHandler.handle(serverRequest)
                .as(StepVerifier::create)
                .expectNextCount(0)
                .verifyComplete();
        verify(clothingService, times(1)).delete(id);
    }

    @Test
    @DisplayName("DEL Delete Clothing Handler: service call returns error")
    public void shouldThrowServiceErrorInDeleteHandler() {
        //Given
        UUID id = UUID.randomUUID();
        ServerRequest serverRequest = mock(ServerRequest.class);

        //When
        when(serverRequest.pathVariable(RouteConfig.ID_VARIABLE))
                .thenReturn(id.toString());
        when(clothingService.delete(id))
                .thenReturn(Mono.error(new R2dbcBadGrammarException()));

        //Then
        deleteClothingHandler.handle(serverRequest)
                .as(StepVerifier::create)
                .assertNext(new Consumer<ServerResponse>() {
                    @Override
                    public void accept(ServerResponse serverResponse) {
                        Assertions.assertTrue(serverResponse.statusCode() == HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                })
                .verifyComplete();
        verify(clothingService, times(1)).delete(id);
    }
}
