package com.ccs.inventorymanagement;

import com.ccs.inventorymanagement.domain.Clothing;
import com.ccs.inventorymanagement.domain.Item;
import com.ccs.inventorymanagement.repo.ClothingEntity;
import com.ccs.inventorymanagement.repo.ClothingRepository;
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
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Instant;
import java.util.UUID;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        when(serverRequest.pathVariable(any(String.class)))
                .thenReturn(String.valueOf(id));
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
        when(serverRequest.bodyToMono(UpdateClothingHandler.Request.class))
                .thenReturn(Mono.just(request));
        when(serverRequest.pathVariable(any(String.class)))
                .thenReturn(String.valueOf(id));
        when(clothingService.update(any(Clothing.class)))
                .thenReturn(Mono.error(new R2dbcBadGrammarException()));

        //Then
        updateClothingHandler.handle(serverRequest)
                .as(StepVerifier::create)
                .expectError(HttpServerErrorException.InternalServerError.class);
        //verify(clothingService, times(1)).update(any(Clothing.class));
    }

    @Test
    @DisplayName("PUT Update Clothing Handler: service call returns empty on findById() call")
    public void shouldReturnNotFoundForGivenId() {
        //Given
        ServerRequest serverRequest = mock(ServerRequest.class);
        UpdateClothingHandler.Request request = mock(UpdateClothingHandler.Request.class);
        UUID id = UUID.randomUUID();

        //When
        when(serverRequest.bodyToMono(UpdateClothingHandler.Request.class))
                .thenReturn(Mono.just(request));
        when(serverRequest.pathVariable(any(String.class)))
                .thenReturn(String.valueOf(id));
        when(clothingService.update(any(Clothing.class)))
                .thenReturn(Mono.empty());

        //Then
        updateClothingHandler.handle(serverRequest)
                .as(StepVerifier::create)
                .expectError();
        verify(clothingService, times(1)).update(any(Clothing.class));
    }
}
