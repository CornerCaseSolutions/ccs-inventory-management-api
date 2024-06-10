package com.ccs.inventorymanagement.route;

import com.ccs.inventorymanagement.domain.Clothing;
import com.ccs.inventorymanagement.domain.Item;
import com.ccs.inventorymanagement.service.ClothingService;
import lombok.Builder;
import lombok.Data;
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
                .flatMap(new Function<Request, Mono<? extends Clothing>>() {
                    @Override
                    public Mono<? extends Clothing> apply(Request request) {
                        Boolean present = null;
                        if(request.getUpdateType() == Request.UpdateType.UPDATE) {
                            present = true;
                        } else if(request.getUpdateType() == Request.UpdateType.DELETE) {
                            present = false;
                        }

                        return service.update(Clothing.builder()
                                .id(UUID.fromString(serverRequest.pathVariable(ID_VARIABLE)))   //ID_VARIABLE will need to change to reference the route config class at some point.
                                .name(request.getName())
                                .description(request.getDescription())
                                .condition(request.getCondition())
                                .present(present)
                                .brand(request.getBrand())
                                .color(request.getColor())
                                .type(request.getType())
                                .gender(request.getGender())
                                .size(request.getSize())
                                .build());
                    }
                }).flatMap(new Function<Clothing, Mono<? extends ServerResponse>>() {
                    @Override
                    public Mono<? extends ServerResponse> apply(Clothing clothing) {
                        return ServerResponse.ok()
                                .body(BodyInserters.fromValue(Response.from(clothing)));
                    }
                });
    }

    @Data
    @Builder
    public static final class Request {
        private UUID id;
        private String name;
        private String description;
        private Item.Condition condition;
        private String brand;
        private String color;
        private Clothing.Type type;
        private Clothing.Gender gender;
        private Clothing.Size size;
        private UpdateType updateType;

        public enum UpdateType {
            UPDATE,
            DELETE
        }
    }

    @Data
    @Builder
    public static final class Response {
        private UUID id;
        private String name;
        private String description;
        private Item.Condition condition;
        private Boolean present;
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
                    .present(clothing.getPresent())
                    .brand(clothing.getBrand())
                    .color(clothing.getColor())
                    .type(clothing.getType())
                    .gender(clothing.getGender())
                    .size(clothing.getSize())
                    .build();
        }
    }
}
