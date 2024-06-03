package com.ccs.inventorymanagement.repo;

import com.ccs.inventorymanagement.domain.Clothing;
import org.springframework.data.relational.core.mapping.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@Table("clothing")
public class ClothingEntity extends ItemEntity{

    @Column
    private String color;

    @Column
    private String fabric;

    public static ClothingEntity from(Clothing clothing) {
        return builder()
                .id(clothing.getId())
                .created(clothing.getCreated())
                .updated(clothing.getUpdated())
                .stock(clothing.getStock())
                .brand(clothing.getBrand())
                .fabric(clothing.getFabric())
                .description(clothing.getDescription())
                .condition(clothing.getCondition())
                .stockStatus(clothing.getStockStatus())
                .build();
    }
}
