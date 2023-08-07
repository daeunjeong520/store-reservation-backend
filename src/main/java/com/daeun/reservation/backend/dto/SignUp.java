package com.daeun.reservation.backend.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

public class SignUp {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request {

        @NotNull
        @Length(max = 50)
        private String username;

        @NotNull
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
