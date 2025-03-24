package com.ccs.inventorymanagement.security;

import com.ccs.inventorymanagement.security.identity.IdentityService;
import com.ccs.inventorymanagement.security.identity.Password;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;

public class AuthenticationService {

    private final IdentityService identityService;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(IdentityService identityService, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.identityService = identityService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public Mono<JsonWebToken> authenticate(String email, Password password) {
        return identityService.findByEmail(email)
                .flatMap(user -> {
                    if (user.getPassword().verify(passwordEncoder, password)) {
                        return jwtService.create(() -> user.getId().toString());
                    }

                    return Mono.error(new RuntimeException("Authentication failed"));
                });
    }

}
