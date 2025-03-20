package com.ccs.inventorymanagement.security;

import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

public interface JwtService {

    Mono<JsonWebToken> create(JwtSpec spec);

    Mono<Boolean> verify(JsonWebToken jwt);

}
