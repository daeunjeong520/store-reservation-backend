package com.daeun.reservation.backend.dto;

import com.daeun.reservation.backend.domain.Reservation;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDto {

    private String storeName;
    private String username;
    private LocalDateTime started;
    private LocalDateTime ended;

    public static ReservationDto from(Reservation reservation) {
        return ReservationDto.builder()
                .storeName(reservation.getStore().getName())
                .username(reservation.getUser().getUsername())
                .started(reservation.getStarted())
                .ended(reservation.getEnded())
                .build();
    }
}
