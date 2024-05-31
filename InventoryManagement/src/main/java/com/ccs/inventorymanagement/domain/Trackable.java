package com.ccs.inventorymanagement.domain;

import java.time.Instant;
import java.util.UUID;

public interface Trackable {
    UUID getId();
    Instant created = Instant.now();
    Instant updated = null;
}
