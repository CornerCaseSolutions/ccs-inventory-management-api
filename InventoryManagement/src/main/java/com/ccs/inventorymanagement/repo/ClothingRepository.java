package com.ccs.inventorymanagement.repo;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface ClothingRepository extends R2dbcRepository<ClothingEntity, UUID> {
    @Query("UPDATE clothing SET status = 'ISSUED' WHERE id = $0")
    Mono<Void> softDeleteById(UUID id);
}