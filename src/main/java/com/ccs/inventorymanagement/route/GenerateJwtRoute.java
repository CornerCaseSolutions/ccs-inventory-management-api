package com.ccs.inventorymanagement.route;

import com.ccs.inventorymanagement.security.JsonWebToken;
import com.ccs.inventorymanagement.security.JwtService;
import com.ccs.inventorymanagement.security.JwtSpec;
import org.springframework.lang.NonNull;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class GenerateJwtRoute implements HandlerFunction<ServerResponse> {

    private final JwtService jwtService;

    public GenerateJwtRoute(JwtService jwtService) {
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
