package com.daeun.reservation.backend.dto;

import com.daeun.reservation.backend.domain.Reservation;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationInfo {

    private String storeName;
    private String username;
    private LocalDateTime started;
    private LocalDateTime ended;

    public static ReservationInfo from(ReservationDto reservationDto) {
        return ReservationInfo.builder()
                .storeName(reservationDto.getStoreName())
                .username(reservationDto.getUsername())
                .started(reservationDto.getStarted())
                .ended(reservationDto.getEnded())
                .build();
    }
}
