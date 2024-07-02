package com.ccs.inventorymanagement.repo;


import com.ccs.inventorymanagement.domain.Clothing;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Getter
@Setter
public class ClothingEntity extends ItemEntity {

    @Column
    private String brand;

    @Column
    private String color;

    @Column
    private Clothing.Type type;

    @Column
    private Clothing.Gender gender;

    @Column
    private Clothing.Size size;

    public static ClothingEntity from(Clothing clothing) {
        return builder()
                .id(clothing.getId())
                .name(clothing.getName())
                .condition(clothing.getCondition())
                .present(clothing.getPresent())
                .description(clothing.getDescription())
                .brand(clothing.getBrand())
                .color(clothing.getColor())
                .type(clothing.getType())
                .gender(clothing.getGender())
                .size(clothing.getSize())
                .build();
    }


}
