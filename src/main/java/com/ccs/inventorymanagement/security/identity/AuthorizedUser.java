package com.ccs.inventorymanagement.security.identity;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class AuthorizedUser implements User {

    private final UUID id;

    private final String email;

    private final Password password;

    private final Role role;

    private final Name name;

    private final Address address;

}