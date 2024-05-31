package com.ccs.inventorymanagement.domain;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;
@Data
@Builder
public class Item implements Trackable {
    private final UUID id;
    private final Instant created;
    private final Instant updated;
    private final int stock;
    private final String description;
    private final Condition condition;
    private final StockStatus stockStatus;

    public enum Condition {
        NEW,
        LIKE_NEW,
        GOOD,
        FAIR,
        USED,
        BAD,
        POOR,
        BROKEN
    }

    public enum StockStatus {
        IN_STOCK,
        OUT_OF_STOCK,
        ISSUED,
        IN_STORAGE,
        THROWN_OUT,
        DELETED,

    }

}
