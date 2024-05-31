package com.ccs.inventorymanagement.domain;

import java.time.LocalDate;
import java.util.UUID;

public interface Trackable {
    UUID getId();
    LocalDate created = LocalDate.now();
    LocalDate updated = null;
}
