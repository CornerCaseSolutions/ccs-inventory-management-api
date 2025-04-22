package com.ccs.inventorymanagement.route;

import com.ccs.inventorymanagement.security.AuthenticationService;
import com.ccs.inventorymanagement.security.JsonWebToken;
import com.ccs.inventorymanagement.security.JwtService;
import com.ccs.inventorymanagement.security.identity.EncodedPassword;
import com.ccs.inventorymanagement.security.identity.Password;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.lang.NonNull;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Function;

public class AuthenticationHandler implements HandlerFunction<ServerResponse> {

    private final AuthenticationService authenticationService;

    public AuthenticationHandler(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @NonNull
    @Override
    public Mono<ServerResponse> handle(@NonNull ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Request.class)
                .flatMap(request -> authenticationService.authenticate(request.email, EncodedPassword.plain(request.password)))
                .flatMap(jsonWebToken -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .cookie(toCookie(jsonWebToken))
                        .body(BodyInserters.fromValue(new Response(jsonWebToken.getToken()))));
    }

    public ResponseCookie toCookie(JsonWebToken jwt) {
        return ResponseCookie.from("jwt", jwt.getToken())
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(Duration.ofMillis(jwt.getExpires().getTime() - System.currentTimeMillis()))
                .build();
    }

    @Data
    @AllArgsConstructor
    public static final class Request {

        private final String email;

        private final String password;

    }

    @Data
    @AllArgsConstructor
    public static final class Response {

        private final String token;

    }

}
