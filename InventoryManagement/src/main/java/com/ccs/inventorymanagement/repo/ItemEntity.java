package com.ccs.inventorymanagement.repo;

import com.ccs.inventorymanagement.domain.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class ItemEntity {
    @Id
    private UUID id;

    @Column
    private String name;

    @Column
    private Item.Condition condition;

    @Column
    private Instant created;

    @Column
    private Instant updated;

    @Column
    private String description;

    @Column
    private boolean present;

}
