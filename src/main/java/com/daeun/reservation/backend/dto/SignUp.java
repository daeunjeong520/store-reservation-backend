package com.daeun.reservation.backend.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class SignUp {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request {

        @NotNull
        @Max(50)
        private String username;

        @NotNull
        @Size(min = 8, max = 50)
        private String password;

        @NotNull
        private List<String> roles;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {

        private Long userId;
        private String username;
        private List<String> roles;

        public static Response from(UserDto userDto) {
            return Response.builder()
                    .userId(userDto.getUserId())
                    .username(userDto.getUsername())
                    .roles(userDto.getRoles())
                    .build();
        }
    }
}
