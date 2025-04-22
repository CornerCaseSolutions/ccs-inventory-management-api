package com.ccs.inventorymanagement.security;

import com.nimbusds.jwt.SignedJWT;
import lombok.Getter;

import java.text.ParseException;
import java.time.Duration;
import java.util.Date;

@Getter
public class JsonWebToken {

    private final String token;

    private Date expires;

    public JsonWebToken(String token) {
        this.token = token;
    }

    public JsonWebToken(String token, Date expires) {
        this.token = token;
        this.expires = expires;
    }

    public static JsonWebToken from(SignedJWT jws) {
        try {
            return new JsonWebToken(jws.serialize(), jws.getJWTClaimsSet().getExpirationTime());
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

}
