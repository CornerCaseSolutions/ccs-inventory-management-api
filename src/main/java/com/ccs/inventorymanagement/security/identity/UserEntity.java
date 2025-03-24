package com.ccs.inventorymanagement.security.identity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Getter
@Builder
@Table("users")
public class UserEntity implements User {

    @Id
    private UUID id;

    private String email;

    private String password;

    private Role role;

    private String firstName;

    private String lastName;

    private String street;

    private String city;

    private String state;

    private String zip;

    @Override
    public Password getPassword() {
        return EncodedPassword.encoded(password);
    }

    @Override
    public Name getName() {
        return Name.builder()
                .first(firstName)
                .last(lastName)
                .build();
    }

    @Override
    public Address getAddress() {
        return Address.builder()
                .street(street)
                .city(city)
                .state(state)
                .zip(zip)
                .build();
    }

}

