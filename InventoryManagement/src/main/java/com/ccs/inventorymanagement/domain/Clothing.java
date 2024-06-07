package com.ccs.inventorymanagement.domain;

import com.ccs.inventorymanagement.repo.ClothingEntity;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
public class Clothing extends Item {
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
    private Apparel_type apparelType;
    private Gender gender;
    private Size size;

    public static Clothing from(ClothingEntity clothingEntity) {
        return Clothing.builder()
                .id(clothingEntity.getId())
                .created(clothingEntity.getCreated())
                .updated(clothingEntity.getUpdated())
                .name(clothingEntity.getName())
                .condition(clothingEntity.getCondition())
                .description(clothingEntity.getDescription())
                .present(clothingEntity.isPresent())
                .brand(clothingEntity.getBrand())
                .color(clothingEntity.getColor())
                .apparelType(clothingEntity.getApparelType())
                .gender(clothingEntity.getGender())
                .size(clothingEntity.getSize())
                .build();
    }

}
