package com.ccs.inventorymanagement.security.identity;

import lombok.Builder;

@Builder
public record Address(String street, String city, String state, String zip) {

    @Override
    public String toString() {
        return "Address[" +
                "street=" + street + ", " +
                "city=" + city + ", " +
                "state=" + state + ", " +
                "zip=" + zip + ']';
    }

}
