package com.ccs.inventorymanagement.security.identity;

import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IdentityService {

    Mono<? extends User> findById(UUID id);

    Mono<? extends User> findByEmail(String email);

    Mono<? extends User> register(User user);

}
