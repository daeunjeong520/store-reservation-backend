package com.daeun.reservation.backend.controller;

import com.daeun.reservation.backend.dto.RegisterReservation;
import com.daeun.reservation.backend.dto.ReservationInfo;
import com.daeun.reservation.backend.dto.constants.ApprovalStatus;
import com.daeun.reservation.backend.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    // 예약 진행
    @PostMapping
    public RegisterReservation.Response registerReservation(
            @RequestBody @Valid RegisterReservation.Request request,
            @RequestParam Long storeId,
            @RequestParam ApprovalStatus status
    ) {
        return RegisterReservation.Response.from(
                reservationService.registerReservation(
                        storeId,
                        request.getDate(),
                        request.getStarted(),
                        request.getEnded(),
                        status
                )
        );
    }

    // 키오스크 - 예약 사용
    @PostMapping("/{reservationId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ReservationInfo useReservation(
            @PathVariable Long reservationId,
            @RequestParam @DateTimeFormat(pattern = "HH:mm:ss") LocalTime arrivedAt
    ) {
        return ReservationInfo.from(
                reservationService.useReservation(reservationId, arrivedAt)
        );
    }

    // 사용자 예약 목록 조회
    @GetMapping("/{storeId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<ReservationInfo> getReservations(
            @PathVariable Long storeId
    ) {
        return reservationService.getReservationList(storeId)
                .stream()
                .map(ReservationInfo::from)
                .collect(Collectors.toList());
    }
}