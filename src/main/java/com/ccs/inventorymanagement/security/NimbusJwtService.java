package com.ccs.inventorymanagement.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class NimbusJwtService implements JwtService {

    private final JWK jwk;

    public NimbusJwtService(JWK jwk) {
        this.jwk = jwk;
    }

    @Override
    public Mono<JsonWebToken> create(JwtSpec spec) {
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(spec.getSubject())
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)))
                .build();
        SignedJWT jws = new SignedJWT(buildJwsHeaderFromJwk(jwk), claims);

        try {
            jws.sign(createSigner(jwk));
        } catch (Exception e) {
            return Mono.error(e);
        }

        return Mono.just(JsonWebToken.from(jws));
    }

    @Override
    public Mono<Boolean> verify(JsonWebToken jwt) {
        try {
            SignedJWT jws = SignedJWT.parse(jwt.getToken());
            JWSVerifier verifier = createVerifier(jwk);

            boolean valid = jws.verify(verifier);
            boolean isExpired = jws.getJWTClaimsSet().getExpirationTime().toInstant().isBefore(Instant.now());

            return Mono.just(valid && !isExpired);
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

    private JWSSigner createSigner(JWK jwk) throws JOSEException {
        return switch (jwk.getKeyType().getValue()) {
            case "RSA" -> new RSASSASigner((RSAKey) jwk);
            case "EC" -> new ECDSASigner((ECKey) jwk);
            case "oct" -> new MACSigner((OctetSequenceKey) jwk);
            default -> throw new JOSEException("Unsupported key type: " + jwk.getKeyType());
        };
    }

    private JWSVerifier createVerifier(JWK jwk) throws JOSEException {
        return switch (jwk.getKeyType().getValue()) {
            case "RSA" -> new RSASSAVerifier((RSAKey) jwk.toPublicJWK());
            case "EC" -> new ECDSAVerifier((ECKey) jwk.toPublicJWK());
            case "oct" -> new MACVerifier((OctetSequenceKey) jwk);
            default -> throw new JOSEException("Unsupported key type: " + jwk.getKeyType());
        };
    }

    private JWSHeader buildJwsHeaderFromJwk(JWK jwk) {
        JWSAlgorithm algorithm = determineAlgorithm(jwk);
        return new JWSHeader.Builder(algorithm)
                .keyID(jwk.getKeyID()) // Include kid if present
                .build();
    }

    private JWSAlgorithm determineAlgorithm(JWK jwk) {
        return switch (jwk.getKeyType().getValue()) {
            case "RSA" -> JWSAlgorithm.RS256;
            case "EC" -> {
                ECKey ecKey = (ECKey) jwk;
                yield switch (ecKey.getCurve().getName()) {
                    case "P-256" -> JWSAlgorithm.ES256;
                    case "P-384" -> JWSAlgorithm.ES384;
                    case "P-521" -> JWSAlgorithm.ES512;
                    default -> throw new IllegalArgumentException("Unsupported EC curve: " + ecKey.getCurve());
                };
            }
            case "oct" -> JWSAlgorithm.HS256;
            default -> throw new IllegalArgumentException("Unsupported key type: " + jwk.getKeyType());
        };
    }

}
