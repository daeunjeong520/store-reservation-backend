package com.daeun.reservation.backend.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class RegisterStore {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request {

        @NotNull
        @Length(max = 100)
        private String name;

        @NotNull
        private String location;

        @NotNull
        @Length(max = 500)
        private String description;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private String managerName;
        private String storeName;
        private String location;
        private String description;

        public static Response from(StoreDto storeDto) {
            return Response.builder()
                    .managerName(storeDto.getManagerName())
                    .storeName(storeDto.getStoreName())
                    .location(storeDto.getLocation())
                    .description(storeDto.getDescription())
                    .build();
        }
    }
}
