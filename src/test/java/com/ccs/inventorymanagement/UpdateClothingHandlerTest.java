package com.ccs.inventorymanagement;

import com.ccs.inventorymanagement.config.RouteConfig;
import com.ccs.inventorymanagement.domain.Clothing;
import com.ccs.inventorymanagement.route.UpdateClothingHandler;
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
public class UpdateClothingHandlerTest {

    @Mock
    ClothingService clothingService;

    @InjectMocks
    UpdateClothingHandler updateClothingHandler;

    @Test
    @DisplayName("PUT Update Clothing Handler: handle() happy path")
    public void shouldUpdateClothingHandler() {
        //Given
        UUID id = UUID.randomUUID();
        ServerRequest serverRequest = mock(ServerRequest.class);
        Clothing clothing = Clothing.builder().build();
        UpdateClothingHandler.Request request = UpdateClothingHandler.Request.builder().build();

        //When
        when(clothingService.update(any(Clothing.class)))
                .thenReturn(Mono.just(clothing));
        when(serverRequest.pathVariable(RouteConfig.ID_VARIABLE))
                .thenReturn(id.toString());
        when(serverRequest.bodyToMono(UpdateClothingHandler.Request.class))
                .thenReturn(Mono.just(request));

        //Then
        updateClothingHandler.handle(serverRequest)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
        verify(clothingService, times(1)).update(clothing);
    }

    @Test
    @DisplayName("PUT Update Clothing Handler: service call returns error")
    public void shouldThrowServiceErrorInUpdateHandler() {
        //Given
        ServerRequest serverRequest = mock(ServerRequest.class);
        UpdateClothingHandler.Request request = UpdateClothingHandler.Request.builder().build();
        UUID id = UUID.randomUUID();

        //When
        when(serverRequest.bodyToMono(eq(UpdateClothingHandler.Request.class)))
                .thenReturn(Mono.just(request));
        when(clothingService.update(any(Clothing.class)))
                .thenReturn(Mono.error(new R2dbcBadGrammarException()));
        when(serverRequest.pathVariable(RouteConfig.ID_VARIABLE))
                .thenReturn(id.toString());

        //Then
        updateClothingHandler.handle(serverRequest)
                .as(StepVerifier::create)
                .assertNext(new Consumer<ServerResponse>() {
                    @Override
                    public void accept(ServerResponse serverResponse) {
                        Assertions.assertTrue(serverResponse.statusCode() == HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                })
                .verifyComplete();
        verify(clothingService, times(1)).update(any(Clothing.class));
    }

    @Test
    @DisplayName("PUT Update Clothing Handler: service call returns empty on findById() call")
    public void shouldReturnNotFoundForGivenId() {
        //Given
        UUID id = UUID.randomUUID();
        ServerRequest serverRequest = mock(ServerRequest.class);
        UpdateClothingHandler.Request request = UpdateClothingHandler.Request.builder().build();

        //When
        when(clothingService.update(any(Clothing.class)))
                .thenReturn(Mono.empty());
        when(serverRequest.pathVariable(any(String.class)))
                .thenReturn(String.valueOf(id));
        when(serverRequest.bodyToMono(UpdateClothingHandler.Request.class))
                .thenReturn(Mono.just(request));

        //Then
        updateClothingHandler.handle(serverRequest)
                .as(StepVerifier::create)
                .assertNext(new Consumer<ServerResponse>() {
                    @Override
                    public void accept(ServerResponse serverResponse) {
                        Assertions.assertTrue(serverResponse.statusCode() == HttpStatus.NOT_FOUND);
                    }
                })
                .verifyComplete();
    }
}
