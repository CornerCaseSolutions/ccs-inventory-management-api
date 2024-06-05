package com.ccs.inventorymanagement.domain;

import com.ccs.inventorymanagement.repo.ClothingEntity;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
public class Clothing extends Item{
    public enum Apparel_type {
        OUTERWEAR,
        SHIRT,
        VEST,
        PANTS,
        UNDERWEAR,
        SOCKS,
        GLOVES,
        SCARF,
        HAT,
        GLASSES,
        JEWELERY,
        SHOES,
        MISCELLANEOUS

    }
    private String brand;
    private String type; //TODO: debate this with jake to use enum or string
    private Apparel_type apparelType;

    public static Clothing from(ClothingEntity clothingEntity) {
        return Clothing.builder()
                .id(clothingEntity.getId())
                .created(clothingEntity.getCreated())
                .updated(clothingEntity.getUpdated())
                .name(clothingEntity.getName())
                .condition(clothingEntity.getCondition())
                .status(clothingEntity.getStatus())
                .description(clothingEntity.getDescription())
                .brand(clothingEntity.getBrand())
                .apparelType(clothingEntity.getApparelType())
                .type(clothingEntity.getType()) //TODO: LOOK INTO THIS
                .build();
    }

}
