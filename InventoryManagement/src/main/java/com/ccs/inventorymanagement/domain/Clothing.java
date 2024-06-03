package com.ccs.inventorymanagement.domain;

import com.ccs.inventorymanagement.repo.ClothingEntity;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class Clothing extends Item{
    private final String color;
    private final String fabric;

    public static Clothing from(ClothingEntity entity) {
        return Clothing.builder()
                .id(entity.getId())
                .created(entity.getCreated())
                .updated(entity.getUpdated())
                .stock(entity.getStock())
                .brand(entity.getBrand())
                .fabric(entity.getFabric())
                .description(entity.getDescription())
                .condition(entity.getCondition())
                .stockStatus(entity.getStockStatus())
                .build();
    }
}
