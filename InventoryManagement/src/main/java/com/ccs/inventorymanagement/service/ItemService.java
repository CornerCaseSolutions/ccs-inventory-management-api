package com.ccs.inventorymanagement.service;

import com.ccs.inventorymanagement.domain.Item;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ItemService<T extends Item> {

    Flux<T> findAll();

    Mono<T> findById(UUID id);

    Mono<T> create(T item);

    Mono<T> update(T item);

    Mono<Void> delete(UUID id);

}
