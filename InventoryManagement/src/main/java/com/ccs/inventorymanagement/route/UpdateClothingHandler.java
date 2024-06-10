package com.ccs.inventorymanagement.route;

import com.ccs.inventorymanagement.domain.Clothing;
import com.ccs.inventorymanagement.domain.Item;
import com.ccs.inventorymanagement.service.ClothingService;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

public class UpdateClothingHandler implements HandlerFunction<ServerResponse> {

    private final ClothingService service;

    public UpdateClothingHandler(ClothingService service) {
        this.service = service;
    }

    @Override
    public Mono<ServerResponse> handle(ServerRequest serverRequest) {
        return null;
    }

    @Data
    @Builder
    public static final class Request {
        private UUID id;
        private String name;
        private String description;
        private Item.Condition condition;
        private boolean present;
        private String brand;
        private String color;
        private Clothing.Apparel_type apparelType;
        private Clothing.Gender gender;
        private Clothing.Size size;
        private Update_type updateType;

        public enum Update_type {
            UPDATE,
            DELETE
        }
    }

    public static final class Response {
        private UUID id;
        private String name;
        private String description;
        private Item.Condition condition;
        private boolean present;
        private String brand;
        private String color;
        private Clothing.Apparel_type apparelType;
        private Clothing.Gender gender;
        private Clothing.Size size;
    }
}
