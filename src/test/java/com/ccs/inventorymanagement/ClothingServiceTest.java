package com.ccs.inventorymanagement;

import com.ccs.inventorymanagement.domain.Clothing;
import com.ccs.inventorymanagement.repo.ClothingEntity;
import com.ccs.inventorymanagement.repo.ClothingRepository;
import com.ccs.inventorymanagement.service.ClothingService;
import io.r2dbc.spi.R2dbcBadGrammarException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClothingServiceTest {

    @Mock
    ClothingRepository clothingRepository;

    @InjectMocks
    ClothingService clothingService;

    @Test
    @DisplayName("GET Find All: Should return all clothing found in the database")
    public void shouldReturnAllClothing() {

        // Given
        final UUID id = UUID.randomUUID();
        ClothingEntity entity = mock(ClothingEntity.class);


        // When
        when(clothingRepository.findAll())
                .thenReturn(Flux.just(entity));
        when(entity.getId())
                .thenReturn(id);


        // Then
        clothingService.findAll()
                .as(StepVerifier::create)
                .assertNext(clothing -> assertEquals(id, clothing.getId()))
                .verifyComplete();
        verify(clothingRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("GET Find By ID: Should return clothing by ID")
    public void shouldReturnClothingById() {

        // Given
        final UUID id = UUID.randomUUID();
        ClothingEntity entity = mock(ClothingEntity.class);

        // When
        when(clothingRepository.findById(id))
                .thenReturn(Mono.just(entity));
        when(entity.getId())
                .thenReturn(id);

        // Then
        clothingService.findById(id)
                .as(StepVerifier::create)
                .assertNext(clothing -> assertEquals(id, clothing.getId()))
                .verifyComplete();
        verify(clothingRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("GET Find By ID: Should not return clothing by ID if does not exist")
    public void shouldNotReturnClothingIfNotFoundById() {

        // Given
        final UUID id = UUID.randomUUID();

        // When
        when(clothingRepository.findById(id))
                .thenReturn(Mono.empty());

        // Then
        clothingService.findById(id)
                .as(StepVerifier::create)
                .verifyComplete();
        verify(clothingRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("GET Find By ID: Should return errors")
    public void shouldReturnErrors() {

        // Given
        final UUID id = UUID.randomUUID();

        // When
        when(clothingRepository.findById(id))
                .thenReturn(Mono.error(new R2dbcBadGrammarException()));

        // Then
        clothingService.findById(id)
                .as(StepVerifier::create)
                .verifyError(R2dbcBadGrammarException.class);
        verify(clothingRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("PUT Update Clothing: Should update clothing by ID")
    public void shouldUpdateClothing() {
        //Given
        final UUID id = UUID.randomUUID();
        Clothing clothing = mock(Clothing.class);
        ClothingEntity entity = mock(ClothingEntity.class);

        //When
        when(clothingRepository.findById(id))
                .thenReturn(Mono.just(entity));
        when(clothing.getId())
                .thenReturn(id);
        when(entity.toBuilder())
                .thenReturn(entity.toBuilder().build());

        //Then
        clothingService.update(clothing)
                .as(StepVerifier::create)
                .verifyComplete();
        verify(clothingRepository, times(1)).save(entity);
    }
}
