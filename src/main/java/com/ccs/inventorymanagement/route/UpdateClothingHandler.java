package com.ccs.inventorymanagement.route;

import com.ccs.inventorymanagement.config.RouteConfig;
import com.ccs.inventorymanagement.domain.Clothing;
import com.ccs.inventorymanagement.domain.Item;
import com.ccs.inventorymanagement.service.ClothingService;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.function.Function;

public class UpdateClothingHandler implements HandlerFunction<ServerResponse> {

    private final ClothingService service;

    public UpdateClothingHandler(ClothingService service) {
        this.service = service;
    }

    @Override
    public Mono<ServerResponse> handle(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Request.class)
                .flatMap(request -> service.update(Clothing.builder()
                        .id(UUID.fromString(serverRequest.pathVariable(RouteConfig.ID_VARIABLE)))
                        .name(request.getName())
                        .description(request.getDescription())
                        .condition(request.getCondition())
                        .status(request.getStatus())
                        .brand(request.getBrand())
                        .color(request.getColor())
                        .type(request.getType())
                        .gender(request.getGender())
                        .size(request.getSize())
                        .build())).flatMap(clothing -> ServerResponse.ok()
                        .body(BodyInserters.fromValue(Response.from(clothing))))
                .onErrorResume(ex -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build())
                .switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND).build());
    }

    @Data
    @Builder
    public static final class Request {
        private UUID id;
        private String name;
        private String description;
        private Item.Condition condition;
        private Item.Status status;
        private String brand;
        private String color;
        private Clothing.Type type;
        private Clothing.Gender gender;
        private Clothing.Size size;
    }

    @Data
    @Builder
    public static final class Response {
        private UUID id;
        private String name;
        private String description;
        private Item.Condition condition;
        private Item.Status status;
        private String brand;
        private String color;
        private Clothing.Type type;
        private Clothing.Gender gender;
        private Clothing.Size size;

        public static Response from(Clothing clothing) {
            return builder()
                    .id(clothing.getId())
                    .name(clothing.getName())
                    .description(clothing.getDescription())
                    .condition(clothing.getCondition())
                    .status(clothing.getStatus())
                    .brand(clothing.getBrand())
                    .color(clothing.getColor())
                    .type(clothing.getType())
                    .gender(clothing.getGender())
                    .size(clothing.getSize())
                    .build();
        }
    }
}