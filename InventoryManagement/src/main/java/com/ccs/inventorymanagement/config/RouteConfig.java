package com.ccs.inventorymanagement.config;

import com.ccs.inventorymanagement.service.ClothingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;

@Configuration
public class RouteConfig {

    public static final String CLOTHING_PATH = "/clothing";
    public static final String ID_VARIABLE = "id";
    public static final String CLOTHING_BY_ID_PATH = CLOTHING_PATH + "/{" + ID_VARIABLE + "}";
    private final ClothingService clothingService;

    @Autowired
    public RouteConfig(ClothingService clothingService) {
        this.clothingService = clothingService;
    }

    @Bean
    public RouterFunction<ServerResponse> findAllClothingRoute() {
        return route(GET(CLOTHING_PATH), findAllClothingHandler());
    }

    @Bean
    public HandlerFunction<ServerResponse> findAllClothingHandler() {
        return new FindAllClothingHandler(clothingService);
    }

    @Bean
    public RouterFunction<ServerResponse> findClothingByIdRoute() {
        return route(GET(CLOTHING_BY_ID_PATH), findClothingByIdHandler());
    }

    @Bean
    public HandlerFunction<ServerResponse> findClothingByIdHandler() {
        return new FindClothingByIdHandler(clothingService);
    }

    @Bean
    public RouterFunction<ServerResponse> createClothingRoute() {
        return route(POST(CLOTHING_PATH), createClothingHandler());
    }

    @Bean
    public HandlerFunction<ServerResponse> createClothingHandler() {
        return new CreateClothingHandler(clothingService);
    }

    @Bean
    public RouterFunction<ServerResponse> updateClothingRoute() {
        return route(PUT(CLOTHING_BY_ID_PATH), updateClothingHandler());
    }

    @Bean
    public HandlerFunction<ServerResponse> updateClothingHandler() {
        return new UpdateClothingHandler(clothingService);
    }

    @Bean
    public RouterFunction<ServerResponse> deleteClothingRoute() {
        return route(DELETE(CLOTHING_BY_ID_PATH), deleteClothingHandler());
    }

    @Bean
    public HandlerFunction<ServerResponse> deleteClothingHandler() {
        return new DeleteClothingHandler(clothingService);
    }
}
