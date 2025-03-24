package com.ccs.inventorymanagement.config;

import com.ccs.inventorymanagement.security.identity.IdentityService;
import com.ccs.inventorymanagement.security.identity.Role;
import com.ccs.inventorymanagement.security.identity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@Profile("dev")
public class DevConfig {

    private final IdentityService identityService;

    @Autowired
    public DevConfig(IdentityService identityService) {
        this.identityService = identityService;
    }

    @Bean
    public RouterFunction<ServerResponse> newUserRoute() {
        return route(POST("/user"), request -> request.bodyToMono(NewUserRequest.class)
                .flatMap(newUserRequest -> identityService.register(UserEntity.builder()
                                .email(newUserRequest.getEmail())
                                .password(newUserRequest.getPassword())
                                .role(newUserRequest.getRole())
                                .firstName(newUserRequest.getFirstName())
                                .lastName(newUserRequest.getLastName())
                                .street(newUserRequest.getStreet())
                                .city(newUserRequest.getCity())
                                .state(newUserRequest.getState())
                                .zip(newUserRequest.getZip())
                                .build())
                        .flatMap(user -> ServerResponse.ok()
                                .body(BodyInserters.fromValue(NewUserResponse.builder()
                                        .id(user.getId().toString())
                                        .email(user.getEmail())
                                        .role(user.getRole())
                                        .firstName(user.getName().first())
                                        .lastName(user.getName().last())
                                        .street(user.getAddress().street())
                                        .city(user.getAddress().city())
                                        .state(user.getAddress().state())
                                        .zip(user.getAddress().zip())
                                        .build())))));
    }

    @Data
    @AllArgsConstructor
    public static final class NewUserRequest {

        private String email;

        private String password;

        private Role role;

        private String firstName;

        private String lastName;

        private String street;

        private String city;

        private String state;

        private String zip;

    }

    @Data
    @Builder
    @AllArgsConstructor
    public static final class NewUserResponse {

        private String id;

        private String email;

        private Role role;

        private String firstName;

        private String lastName;

        private String street;

        private String city;

        private String state;

        private String zip;

    }

}
