package com.daeun.reservation.backend.dto;

import lombok.*;

public class SignIn {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request {
        private String username;
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
