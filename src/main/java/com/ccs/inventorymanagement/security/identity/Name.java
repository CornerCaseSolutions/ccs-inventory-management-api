package com.ccs.inventorymanagement.security.identity;

import lombok.Builder;

@Builder
public record Name(String first, String last) {

}
