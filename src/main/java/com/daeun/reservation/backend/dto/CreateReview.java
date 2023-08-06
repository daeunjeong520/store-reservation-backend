package com.daeun.reservation.backend.dto;

import com.daeun.reservation.backend.dto.constants.Rating;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class CreateReview {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request {
        private String title;
        private String content;
        private Rating rating;
    }
}
