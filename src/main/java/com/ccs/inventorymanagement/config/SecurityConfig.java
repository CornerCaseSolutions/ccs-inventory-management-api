package com.ccs.inventorymanagement.config;

import com.ccs.inventorymanagement.security.JwtAuthenticationConverter;
import com.ccs.inventorymanagement.security.JwtAuthenticationManager;
import com.ccs.inventorymanagement.security.JwtService;
import com.ccs.inventorymanagement.security.NimbusJwtService;
import com.nimbusds.jose.jwk.JWK;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

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
                        .pathMatchers("/jwt/generate").permitAll()
                        .anyExchange().authenticated())
                .addFilterAt(jwtFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

    private AuthenticationWebFilter jwtFilter() throws IOException, ParseException {
        AuthenticationWebFilter filter = new AuthenticationWebFilter(authenticationManager());
        filter.setServerAuthenticationConverter(new JwtAuthenticationConverter());
        return filter;
    }

}
