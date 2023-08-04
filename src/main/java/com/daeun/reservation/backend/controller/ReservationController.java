package com.daeun.reservation.backend.controller;

import com.daeun.reservation.backend.dto.ReservationInfo;
import com.daeun.reservation.backend.repository.StoreRepository;
import com.daeun.reservation.backend.repository.UserRepository;
import com.daeun.reservation.backend.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    // 매장 예약
    @PostMapping("/{storeId}")
    public ReservationInfo registerReservation(
            @RequestParam LocalDateTime started,
            @RequestParam LocalDateTime ended,
            @PathVariable Long storeId
    ) {
        
    }
}
