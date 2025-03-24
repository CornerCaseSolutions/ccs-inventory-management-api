package com.ccs.inventorymanagement.security.identity;

import java.util.UUID;

public interface User {

    UUID getId();

    String getEmail();

    Password getPassword();

    Role getRole();

    Name getName();

    Address getAddress();

}
