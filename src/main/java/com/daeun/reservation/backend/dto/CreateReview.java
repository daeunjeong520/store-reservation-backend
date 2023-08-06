package com.daeun.reservation.backend.dto;

import com.daeun.reservation.backend.dto.constants.Rating;
import lombok.*;

public class CreateReview {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request {
        private String title;
        private String content;
        private Rating rating;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {

        private Long reviewId;
        private String username;
        private String storeName;
        private Rating rating;

        public static Response from(ReviewDto reviewDto) {
            return Response.builder()
                    .reviewId(reviewDto.getReviewId())
                    .username(reviewDto.getUsername())
                    .storeName(reviewDto.getStore().getName())
                    .rating(reviewDto.getRating())
                    .build();
        }
    }
}
