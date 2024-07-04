package com.ccs.inventorymanagement.config;

import com.ccs.inventorymanagement.repo.ClothingRepository;
import com.ccs.inventorymanagement.service.ClothingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {
    private final ClothingRepository repository;

    @Autowired
    public ServiceConfig(ClothingRepository repository) {
        this.repository = repository;
    }

    @Bean
    public ClothingService clothingService() {
        return new ClothingService(repository);
    }
}