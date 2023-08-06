package com.daeun.reservation.backend.dto;

import com.daeun.reservation.backend.domain.Review;
import com.daeun.reservation.backend.dto.constants.Rating;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {

    private Long reviewId;
    private String username;
    private StoreDto store;
    private String title;
    private String content;
    private Rating rating;

    public static ReviewDto fromEntity(Review review) {
        return ReviewDto.builder()
                .reviewId(review.getReviewId())
                .username(review.getUser().getUsername())
                .store(StoreDto.fromEntity(review.getStore()))
                .title(review.getTitle())
                .content(review.getContent())
                .rating(review.getRating())
                .build();
    }
}
