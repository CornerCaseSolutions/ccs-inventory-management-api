package com.ccs.inventorymanagement.service;

import com.ccs.inventorymanagement.domain.Clothing;
import com.ccs.inventorymanagement.domain.Item;
import com.ccs.inventorymanagement.repo.ClothingEntity;
import com.ccs.inventorymanagement.repo.ClothingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

public class ClothingService implements ItemService<Clothing> {

    private final ClothingRepository clothingRepository;

    public ClothingService(ClothingRepository clothingRepository) {
        this.clothingRepository = clothingRepository;
    }

    @Override
    public Flux<Clothing> findAll() {
        return clothingRepository.findAll()
                .map(Clothing::from);
    }

    @Override
    public Mono<Clothing> findById(UUID id) {
        return clothingRepository.findById(id)
                .map(Clothing::from);
    }

    @Override
    public Mono<Clothing> create(Clothing item) {
        return clothingRepository.save(ClothingEntity.from(item)
                 .toBuilder()
                 .created(Instant.now())
                 .build())
                 .map(Clothing::from);
    }

    @Override
    public Mono<Clothing> update(Clothing item) {
        return clothingRepository.findById(item.getId())
        .map(entity -> entity.toBuilder()
                .name(item.getName())
                .description(item.getDescription())
                .condition(item.getCondition())
                .status(item.getStatus())
                .brand(item.getBrand())
                .color(item.getColor())
                .type(item.getType())
                .gender(item.getGender())
                .size(item.getSize())
                .updated(Instant.now())
                .build())
                .flatMap(updatedEntity -> clothingRepository.save(updatedEntity)).map(Clothing::from);
    }

    @Override
    public Mono<Void> delete(UUID id) {
        return clothingRepository.findById(id).map(entity -> entity.toBuilder()
                .status(Item.Status.DELETED)
                .build())
                .flatMap(deletedEntity -> clothingRepository.save(deletedEntity)).then();
    }
}
