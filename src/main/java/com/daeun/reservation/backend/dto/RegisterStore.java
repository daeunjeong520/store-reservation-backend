package com.daeun.reservation.backend.dto;

import lombok.*;

public class RegisterStore {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request {
        private String name;
        private String location;
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
