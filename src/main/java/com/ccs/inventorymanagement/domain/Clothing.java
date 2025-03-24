package com.ccs.inventorymanagement.domain;

import com.ccs.inventorymanagement.domain.user.Name;
import com.ccs.inventorymanagement.repo.ClothingEntity;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class Clothing extends Item {
    public enum Type {
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

    public enum Gender {
        MALE,
        FEMALE,
        UNISEX
    }

    public enum Size {
        EXTRA_SMALL,
        SMALL,
        MEDIUM,
        LARGE,
        EXTRA_LARGE
    }


    private String brand;
    private String color;
    private Type type;
    private Gender gender;
    private Size size;

    public static Clothing from(ClothingEntity clothingEntity) {
        return Clothing.builder()
                .id(clothingEntity.getId())
                .name(clothingEntity.getName())
                .condition(clothingEntity.getCondition())
                .description(clothingEntity.getDescription())
                .status(clothingEntity.getStatus())
                .brand(clothingEntity.getBrand())
                .color(clothingEntity.getColor())
                .type(clothingEntity.getType())
                .gender(clothingEntity.getGender())
                .size(clothingEntity.getSize())
                .created(clothingEntity.getCreated())
                .updated(clothingEntity.getUpdated())
                .build();
    }

}
