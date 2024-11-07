package com.ccs.inventorymanagement.route;

import com.ccs.inventorymanagement.domain.Clothing;
import com.ccs.inventorymanagement.domain.Item;
import com.ccs.inventorymanagement.service.ClothingService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

//import java.time.Instant;
import java.util.UUID;

public class CreateClothingHandler implements HandlerFunction<ServerResponse> {

    private ClothingService clothingService;

    public CreateClothingHandler(ClothingService clothingService) {
        this.clothingService = clothingService;
    }
    @Override
    public Mono<ServerResponse> handle(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Request.class)
                .flatMap(request -> clothingService.create(Clothing.builder()
                        .name(request.getName())
                        .condition(request.getCondition())
                        .status(request.getStatus())
                        .description(request.getDescription())
                        .brand(request.getBrand())
                        .color(request.getColor())
                        .type(request.getType())
                        .gender(request.getGender())
                        .size(request.getSize())
                        .build()))
                .flatMap(clothing -> ServerResponse.ok()
                        .body(BodyInserters.fromValue(Response.from(clothing))))
                .onErrorResume(ex -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static final class Request {
        private String name;
        private final Item.Condition condition;
        private Item.Status status;
        private String description;
        private String brand;
        private String color;
        private Clothing.Type type;
        private Clothing.Gender gender;
        private Clothing.Size size;
    }

    @Data
    @Builder
    public static final class Response {
        private UUID id;
        private String name;
        private final Item.Condition condition;
        //We can't get created without doing clothingRepository.findById(id).created() AFAIK so removing it for now; we can come back and implement this later if wanted
        //private final Instant created;
        private Item.Status status;
        private String description;
        private String brand;
        private String color;
        private Clothing.Type type;
        private Clothing.Gender gender;
        private Clothing.Size size;

        public static Response from(Clothing clothing) {
            return builder()
                    .id(clothing.getId())
                    .name(clothing.getName())
                    .condition(clothing.getCondition())
                    .status(clothing.getStatus())
                    .description(clothing.getDescription())
                    .brand(clothing.getBrand())
                    .color(clothing.getColor())
                    .type(clothing.getType())
                    .gender(clothing.getGender())
                    .size(clothing.getSize())
                    .build();
        }
    }
}
