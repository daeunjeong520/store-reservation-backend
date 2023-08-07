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

    @Column(name = "title", nullable = false, length = 100)
    private String title; // 리뷰 제목

    @Column(name = "content", nullable = false, length = 500)
    private String content; // 리뷰 내용

    @Enumerated(EnumType.STRING)
    @Column(name = "rating", nullable = false)
    private Rating rating; // 별점
}
