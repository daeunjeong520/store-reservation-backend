package com.daeun.reservation.backend.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

public class RegisterStore {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request {

        @NotNull
        @Max(100)
        private String name;

        @NotNull
        private String location;

        @NotNull
        @Max(500)
        private String description;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private String username;
        private String name;
        private String location;
        private String description;

        public static Response from(StoreDto storeDto) {
            return Response.builder()
                    .username(storeDto.getManagerName())
                    .name(storeDto.getName())
                    .location(storeDto.getLocation())
                    .description(storeDto.getDescription())
                    .build();
        }
    }
}
