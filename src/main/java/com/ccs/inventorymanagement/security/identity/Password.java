package com.ccs.inventorymanagement.security.identity;

import org.springframework.security.crypto.password.PasswordEncoder;

public interface Password {

    String getValue();

    boolean isEncoded();

    Password encode(PasswordEncoder encoder);

    boolean verify(PasswordEncoder encoder, Password password);

}
