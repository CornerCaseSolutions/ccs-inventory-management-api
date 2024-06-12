package com.ccs.inventorymanagement.route;

import com.ccs.inventorymanagement.config.RouteConfig;
import com.ccs.inventorymanagement.domain.Clothing;
import com.ccs.inventorymanagement.service.ClothingService;
import lombok.Builder;
import lombok.Data;
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
        final Request request = Request.from(serverRequest);
        return service.findById(request.getId())
                .flatMap(new Function<Clothing, Mono<? extends ServerResponse>>() {
                    @Override
                    public Mono<? extends ServerResponse> apply(Clothing clothing) {
                        return ServerResponse.ok()
                                .build();
                    }
                });
    }

    @Data
    @Builder
    public static final class Request {
        private UUID id;

        public static Request from(ServerRequest serverRequest) {
            return builder()
                    .id(UUID.fromString(serverRequest.pathVariable(RouteConfig.ID_VARIABLE)))
                    .build();
        }
    }
}
