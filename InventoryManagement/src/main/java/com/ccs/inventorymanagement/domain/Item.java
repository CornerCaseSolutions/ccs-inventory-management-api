package com.ccs.inventorymanagement.domain;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

@Data
@SuperBuilder
public class Item implements Trackable {

    public enum Condition {
        EXCELLENT,
        FAIR,
        POOR,
        DAMAGED,
        DESTROYED,
    }

    private final UUID id;
    private String name;
    private Condition condition;
    private boolean present;
    private final Instant created;//this should not change
    private Instant updated;
    private String description;
}
