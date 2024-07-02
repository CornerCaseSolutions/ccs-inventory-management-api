package com.ccs.inventorymanagement.repo;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClothingRepository extends R2dbcRepository<ClothingEntity, UUID> {
}