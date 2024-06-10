package com.ccs.inventorymanagement.route;

import com.ccs.inventorymanagement.service.ClothingService;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

public class FindAllClothingHandler implements HandlerFunction<ServerResponse> {

    private final ClothingService service;

    public FindAllClothingHandler(ClothingService service) {
        this.service = service;
    }

    @Override
    public Mono<ServerResponse> handle(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Req);
    }

    @Data
    @Builder
    public static final class Request {
        private UUID id;
    }
}
