package com.daeun.reservation.backend.controller;

import com.daeun.reservation.backend.dto.CreateReview;
import com.daeun.reservation.backend.dto.ReservationInfo;
import com.daeun.reservation.backend.service.ReservationService;
import com.daeun.reservation.backend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final ReservationService reservationService;

    // 리뷰 작성 가능한 예약 목록 조회
    @GetMapping("/{storeId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<ReservationInfo> getReservations(
            @PathVariable Long storeId
    ) {
        return reservationService.getUsedReservationList(storeId)
                .stream()
                .map(ReservationInfo::from)
                .collect(Collectors.toList());
    }

    // 리뷰 등록
    @PostMapping("/{reservationId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public CreateReview.Response createReview(
            @RequestBody @Valid CreateReview.Request request,
            @PathVariable Long reservationId
    ) {
        return CreateReview.Response.from(
                reviewService.createReview(
                        reservationId,
                        request.getTitle(),
                        request.getContent(),
                        request.getRating()
                )
        );
    }
}