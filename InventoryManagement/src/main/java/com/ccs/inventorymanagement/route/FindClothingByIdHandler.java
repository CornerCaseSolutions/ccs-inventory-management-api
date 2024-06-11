package com.ccs.inventorymanagement.route;


import com.ccs.inventorymanagement.config.RouteConfig;
import com.ccs.inventorymanagement.domain.Clothing;
import com.ccs.inventorymanagement.service.ClothingService;
import com.ccs.inventorymanagement.domain.Item;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

public class FindClothingByIdHandler implements HandlerFunction<ServerResponse> {

    private final ClothingService clothingService;

    public FindClothingByIdHandler(ClothingService clothingService) {
        this.clothingService = clothingService;
    }

    @Override
    public Mono<ServerResponse> handle(ServerRequest serverRequest) {
        final Request request = Request.from(serverRequest);
        return clothingService.findById(request.id)
                .flatMap(clothing -> ServerResponse.ok()
                        .body(BodyInserters.fromValue(Response.from(clothing))))
                .switchIfEmpty(ServerResponse.notFound()
                        .build());
    }


    @Data
    @Builder
    public static final class Request {
        private UUID id;
        public static Request from(ServerRequest request) {
            return builder()
                    .id(UUID.fromString(request.pathVariable(RouteConfig.ID_VARIABLE)))
                    .build();
        }
    }

    @Data
    @Builder
    public static final class Response {
        private final UUID id;
        private String name;
        private final Item.Condition condition;
        private boolean present;
        private final Instant created;//this should not change
        private Instant updated;
        private String description;
        private String brand;
        private String color;
        private Clothing.Type apparelType;
        private Clothing.Gender gender;
        private Clothing.Size size;

        public static Response from(Clothing clothing) {
            return Response.builder()
                    .id(clothing.getId())
                    .name(clothing.getName())
                    .condition(clothing.getCondition())
                    .description(clothing.getDescription())
                    .present(clothing.getPresent())
                    .brand(clothing.getBrand())
                    .color(clothing.getColor())
                    .apparelType(clothing.getType())
                    .gender(clothing.getGender())
                    .size(clothing.getSize())
                    .build();
        }
    }
}
