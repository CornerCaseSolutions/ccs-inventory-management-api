package com.ccs.inventorymanagement.service;

import com.ccs.inventorymanagement.domain.Clothing;
import com.ccs.inventorymanagement.repo.ClothingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public class ClothingService implements ItemService<Clothing> {

    private final ClothingRepository repository;
    public ClothingService(ClothingRepository repository) {
        this.repository = repository;
    }

    public Flux<Clothing> findAll() {
        return null;
    }

    public Mono<Clothing> findById(UUID id) {
        return null;
    }

    public Mono<Clothing> create(Clothing clothing) {
        return null;
    }

    public Mono<Clothing> update(Clothing clothing) {
        return null;
    }

    public Mono<Void> delete(UUID id) {
        return repository.deleteById(id).then();
    }
}
