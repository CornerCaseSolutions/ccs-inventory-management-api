package com.ccs.inventorymanagement.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public class JwtAuthentication implements Authentication {

    private final JsonWebToken jwt;

    private final boolean authenticated;

    public JwtAuthentication(JsonWebToken jwt) {
        this(jwt, false);
    }

    public JwtAuthentication(JsonWebToken jwt, boolean authenticated) {
        this.jwt = jwt;
        this.authenticated = authenticated;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public JsonWebToken getCredentials() {
        return jwt;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return "";
    }
}
