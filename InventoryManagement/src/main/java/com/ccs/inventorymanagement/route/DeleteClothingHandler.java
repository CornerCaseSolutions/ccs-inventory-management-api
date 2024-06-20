package com.ccs.inventorymanagement.route;

import com.ccs.inventorymanagement.config.RouteConfig;
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

public class DeleteClothingHandler implements HandlerFunction<ServerResponse> {

    private final ClothingService service;

    public DeleteClothingHandler(ClothingService service) {
        this.service = service;
    }

    @Override
    public Mono<ServerResponse> handle(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UpdateClothingHandler.Request.class)
                .flatMap(new Function<UpdateClothingHandler.Request, Mono<? extends Clothing>>() {
                    @Override
                    public Mono<? extends Clothing> apply(UpdateClothingHandler.Request request) {
                        return service.update(Clothing.builder()
                                .id(UUID.fromString(serverRequest.pathVariable(RouteConfig.ID_VARIABLE)))
                                .name(request.getName())
                                .description(request.getDescription())
                                .condition(request.getCondition())
                                .status(Item.Status.ISSUED)
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
                                .body(BodyInserters.fromValue(UpdateClothingHandler.Response.from(clothing)));
                    }
                })
                .onErrorResume(ex -> ServerResponse.badRequest().build());
    }
}