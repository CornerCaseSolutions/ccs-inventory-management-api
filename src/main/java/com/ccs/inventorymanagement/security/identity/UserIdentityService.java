package com.ccs.inventorymanagement.security.identity;

import reactor.core.publisher.Mono;

import java.util.UUID;

public class UserIdentityService implements IdentityService {

    private final UserRepository userRepository;

    public UserIdentityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<? extends User> findById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public Mono<? extends User> findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public Mono<? extends User> register(User user) {
        return null;
    }

}
