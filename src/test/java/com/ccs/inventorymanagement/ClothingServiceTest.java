package com.ccs.inventorymanagement;

import com.ccs.inventorymanagement.domain.Clothing;
import com.ccs.inventorymanagement.repo.ClothingEntity;
import com.ccs.inventorymanagement.repo.ClothingRepository;
import com.ccs.inventorymanagement.service.ClothingService;
import io.r2dbc.spi.R2dbcBadGrammarException;
import org.junit.jupiter.api.Assertions;
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
import java.util.function.Consumer;

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
                .expectNextCount(1)
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
                .verifyComplete(); // makes sure reactive chain completes
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
//        ClothingEntity entity = mock(ClothingEntity.class); // create an actual entity
        ClothingEntity entity = ClothingEntity.builder().build();
        Instant initialTime = Instant.now();
        ClothingEntity saved = ClothingEntity.builder().updated(initialTime).build();
        //When
        when(clothingRepository.findById(id))
                .thenReturn(Mono.just(entity));
        when(clothingRepository.save(any(ClothingEntity.class)))
                .thenReturn(Mono.just(saved));
        when(clothing.getId())
                .thenReturn(id);

        //Then
        clothingService.update(clothing)
                .as(StepVerifier::create)
                .assertNext(new Consumer<Clothing>() {
                    @Override
                    public void accept(Clothing clothing) {
                        Assertions.assertEquals(initialTime, clothing.getUpdated());
                    }
                })
                .verifyComplete();
        verify(clothingRepository, times(1)).save(any(ClothingEntity.class));
    }
}
