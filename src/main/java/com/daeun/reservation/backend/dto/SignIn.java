package com.daeun.reservation.backend.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SignIn {

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
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private String token;
    }
}
