package com.ccs.inventorymanagement.route;

import com.ccs.inventorymanagement.security.JwtService;
import org.springframework.lang.NonNull;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class GenerateJwtHandler implements HandlerFunction<ServerResponse> {

    private final JwtService jwtService;

    public GenerateJwtHandler(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @NonNull
    @Override
    public Mono<ServerResponse> handle(@NonNull ServerRequest request) {
        return jwtService.create(() -> "test")
                .flatMap(jsonWebToken -> ServerResponse.ok()
                        .body(BodyInserters.fromValue(jsonWebToken)));
    }

}
