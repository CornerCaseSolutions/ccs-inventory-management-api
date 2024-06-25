package com.ccs.inventorymanagement.route;

import com.ccs.inventorymanagement.domain.Clothing;
import com.ccs.inventorymanagement.domain.Item;
import com.ccs.inventorymanagement.service.ClothingService;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class FindAllHandler implements HandlerFunction<ServerResponse> {

    private final ClothingService clothingService;
    public FindAllHandler(ClothingService clothingService) {
        this.clothingService = clothingService;
    }
    @Override
    public Mono<ServerResponse> handle(ServerRequest request) {
        return clothingService.findAll()
                .collectList()
                .flatMap(clothingList -> ServerResponse.ok()
                        .body(BodyInserters.fromValue(Response.from(clothingList))))
                .onErrorResume(ex -> ServerResponse.status(HttpStatusCode.valueOf(500))
                        .build())
                .switchIfEmpty(ServerResponse.notFound()
                        .build());
    }

    @Data
    @Builder
    public static final class Response {
        private final List<Entry> entryList;
        public static Response from(Collection<Clothing> clothing) {
            return builder()
                    .entryList(clothing.stream()
                            .map(Entry::from)
                            .collect(Collectors.toList()))
                    .build();
        }

    }

    @Data
    @Builder
    public static final class Entry {
        private final UUID id;
        private String name;
        private final Item.Condition condition;
        private boolean present;
        private final Instant created;//this should not change
        private Item.Status status;
        private Instant updated;
        private String description;
        private String brand;
        private String color;
        private Clothing.Type apparelType;
        private Clothing.Gender gender;
        private Clothing.Size size;

        public static Entry from(Clothing clothing) {
            return Entry.builder()
                    .id(clothing.getId())
                    .name(clothing.getName())
                    .condition(clothing.getCondition())
                    .description(clothing.getDescription())
                    .status(clothing.getStatus())
                    .brand(clothing.getBrand())
                    .color(clothing.getColor())
                    .apparelType(clothing.getType())
                    .gender(clothing.getGender())
                    .size(clothing.getSize())
                    .build();
        }
    }
}
