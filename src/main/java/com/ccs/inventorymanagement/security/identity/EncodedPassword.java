package com.ccs.inventorymanagement.security.identity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
public class EncodedPassword implements Password {

    private final String value;

    private final boolean encoded;

    public static EncodedPassword plain(String password) {
        return new EncodedPassword(password, true);
    }

    public static EncodedPassword encoded(String password) {
        return new EncodedPassword(password, false);
    }

    private EncodedPassword(String value, boolean encoded) {
        this.value = value;
        this.encoded = encoded;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public boolean isEncoded() {
        return this.encoded;
    }

    @Override
    public Password encode(PasswordEncoder encoder) {
        return encoded(encoder.encode(value));
    }

    @Override
    public boolean verify(PasswordEncoder encoder, Password password) {
        if (!encoded) {
            log.warn("Attempted to verify plaintext password");
            return false;
        }
        return encoder.matches(password.getValue(), this.value);
    }

}
