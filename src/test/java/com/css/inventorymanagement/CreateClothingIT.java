package com.css.inventorymanagement;

import com.ccs.inventorymanagement.InventoryManagementApplication;
import com.ccs.inventorymanagement.config.RouteConfig;
import com.ccs.inventorymanagement.domain.Clothing;
import com.ccs.inventorymanagement.domain.Item;
import com.ccs.inventorymanagement.repo.ClothingEntity;
import com.ccs.inventorymanagement.repo.ClothingRepository;
import com.ccs.inventorymanagement.route.CreateClothingHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = InventoryManagementApplication.class,
        properties = {
                "spring.profiles.active=test",
                "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration"
        }
)
public class CreateClothingIT {

    @Autowired
    WebTestClient webClient;

    @MockBean
    ClothingRepository clothingRepository;

    @BeforeEach
    public void setup() {
        Mockito.reset(clothingRepository);
    }

    @Test
    public void createClothing() {

        final UUID id = UUID.randomUUID();
        final Instant created = Instant.now();
        final Instant updated = Instant.now();
        final CreateClothingHandler.Request request = CreateClothingHandler.Request.builder()
                .name("Hat")
                .condition(Item.Condition.EXCELLENT)
                .status(Item.Status.ISSUED)
                .description("An article of clothing")
                .brand("Carhartt")
                .color("Brown")
                .type(Clothing.Type.HAT)
                .gender(Clothing.Gender.MALE)
                .size(Clothing.Size.EXTRA_LARGE)
                .build();

        final ClothingEntity clothingEntity = ClothingEntity.builder()
                .id(id)
                .name(request.getName())
                .condition(request.getCondition())
                .status(request.getStatus())
                .description(request.getDescription())
                .brand(request.getBrand())
                .color(request.getColor())
                .type(request.getType())
                .gender(request.getGender())
                .size(request.getSize())
                .created(created)
                .updated(updated)
                .build();

        when(clothingRepository.save(any(ClothingEntity.class)))
                .thenReturn(Mono.just(clothingEntity));

        webClient.post()
                .uri(RouteConfig.CLOTHING_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CreateClothingHandler.Request.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CreateClothingHandler.Response.class)
                .value(response -> {
                    assertEquals(id, response.getId());
                    assertEquals(request.getName(), response.getName());
                    assertEquals(request.getCondition(), response.getCondition());
                    assertEquals(request.getStatus(), response.getStatus());
                    assertEquals(request.getDescription(), response.getDescription());
                    assertEquals(request.getBrand(), response.getBrand());
                    assertEquals(request.getColor(), response.getColor());
                    assertEquals(request.getType(), response.getType());
                    assertEquals(request.getGender(), response.getGender());
                    assertEquals(request.getSize(), response.getSize());
                });
    }

}
