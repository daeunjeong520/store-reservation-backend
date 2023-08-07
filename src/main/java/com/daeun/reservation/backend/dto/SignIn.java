package com.daeun.reservation.backend.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SignIn {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request {

        @NotNull
        @Length(max = 50)
        private String username;

        @NotNull
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
