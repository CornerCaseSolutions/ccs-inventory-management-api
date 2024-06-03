package com.ccs.inventorymanagement.service;

import com.ccs.inventorymanagement.domain.Item;
import com.ccs.inventorymanagement.repo.ItemEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public class DefaultItemService<E extends ItemEntity, T extends Item> implements ItemService<T> {

    private final ReactiveCrudRepository<E, UUID> repository;
    public DefaultItemService(ReactiveCrudRepository<E, UUID> repository) {
        this.repository = repository;
    }

    @Override
    public Flux<T> findAll() {
        return null;
    }

    @Override
    public Mono<T> findById(UUID id) {
        return null;
    }

    @Override
    public Mono<T> create(T item) {
        return null;
    }

    @Override
    public Mono<T> update(T item) {
        return null;
    }

    @Override
    public Mono<Void> delete(UUID id) {
        return null;
    }
}
