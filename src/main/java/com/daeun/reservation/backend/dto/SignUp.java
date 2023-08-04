package com.daeun.reservation.backend.dto;

import lombok.*;

import java.util.List;

public class SignUp {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request {

        private String username;
        private String password;
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
