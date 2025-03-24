package com.ccs.inventorymanagement.security.identity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Getter
@Builder
@Table("users")
public class UserEntity implements User {

    @Id
    private UUID id;

    @Column("email")
    private String email;

    @Column("password")
    private String password;

    @Column("role")
    private Role role;

    @Column("firstName")
    private String firstName;

    @Column("lastName")
    private String lastName;

    @Column("street")
    private String street;

    @Column("city")
    private String city;

    @Column("state")
    private String state;

    @Column("zip")
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

