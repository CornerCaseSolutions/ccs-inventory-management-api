package com.ccs.inventorymanagement.security;

import com.nimbusds.jwt.SignedJWT;
import lombok.Getter;

@Getter
public class JsonWebToken {

    private final String token;

    public JsonWebToken(String token) {
        this.token = token;
    }

    public static JsonWebToken from(SignedJWT jws) {
        return new JsonWebToken(jws.serialize());
    }

}
