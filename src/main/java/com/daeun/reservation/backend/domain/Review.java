package com.daeun.reservation.backend.domain;

import com.daeun.reservation.backend.dto.constants.Rating;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    private String title; // 리뷰 제목
    private String content; // 리뷰 내용

    @Enumerated(EnumType.STRING)
    private Rating rating; // 별점
}
