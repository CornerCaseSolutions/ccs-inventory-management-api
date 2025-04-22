package com.ccs.inventorymanagement.security;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtService jwtService;

    public JwtAuthenticationManager(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        if (authentication instanceof JwtAuthentication) {
            return authenticate(((JwtAuthentication) authentication).getCredentials());
        }
        return authenticate(new JsonWebToken(authentication.getCredentials().toString()));
    }

    public Mono<Authentication> authenticate(JsonWebToken authentication) {
        return jwtService.verify(authentication)
                .map(valid -> new JwtAuthentication(new JsonWebToken(authentication.getToken()), valid));
    }

}
