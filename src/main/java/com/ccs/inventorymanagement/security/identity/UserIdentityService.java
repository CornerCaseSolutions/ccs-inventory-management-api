package com.ccs.inventorymanagement.security.identity;

import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

public class UserIdentityService implements IdentityService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserIdentityService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
        return findByEmail(user.getEmail())
                .flatMap((Function<User, Mono<? extends User>>) user1 -> Mono.error(new RuntimeException("User already exists")))
                .switchIfEmpty(Mono.defer(() -> userRepository.save(UserEntity.builder()
                                .email(user.getEmail())
                                .password(user.getPassword().encode(passwordEncoder).getValue())
                                .role(user.getRole())
                                .firstName(user.getName().first())
                                .lastName(user.getName().last())
                                .street(user.getAddress().street())
                                .city(user.getAddress().city())
                                .state(user.getAddress().state())
                                .zip(user.getAddress().zip())
                        .build())));

    }

}
