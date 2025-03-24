package com.ccs.inventorymanagement.config;

import com.ccs.inventorymanagement.security.*;
import com.ccs.inventorymanagement.security.identity.IdentityService;
import com.ccs.inventorymanagement.security.identity.UserIdentityService;
import com.ccs.inventorymanagement.security.identity.UserRepository;
import com.nimbusds.jose.jwk.JWK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final UserRepository userRepository;

    @Autowired
    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    JWK jwk() throws IOException, ParseException {
        ClassPathResource resource = new ClassPathResource("security/jwk.json");
        return JWK.parse(new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8));
    }

    @Bean
    JwtService jwtService() throws IOException, ParseException {
        return new NimbusJwtService(jwk());
    }

    @Bean
    ReactiveAuthenticationManager authenticationManager() throws IOException, ParseException {
        return new JwtAuthenticationManager(jwtService());
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws IOException, ParseException {
        return http
                .csrf().disable()
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/login").permitAll()
                        .pathMatchers("/user").permitAll()
                        .anyExchange().authenticated())
                .addFilterAt(jwtFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    IdentityService identityService() {
        return new UserIdentityService(userRepository, passwordEncoder());
    }

    @Bean
    AuthenticationService authenticationService() throws IOException, ParseException {
        return new AuthenticationService(identityService(), jwtService(), passwordEncoder());
    }

    private AuthenticationWebFilter jwtFilter() throws IOException, ParseException {
        AuthenticationWebFilter filter = new AuthenticationWebFilter(authenticationManager());
        filter.setServerAuthenticationConverter(new JwtAuthenticationConverter());
        return filter;
    }

}
