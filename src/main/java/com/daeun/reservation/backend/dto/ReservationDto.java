package com.daeun.reservation.backend.dto;

import com.daeun.reservation.backend.domain.Reservation;
import com.daeun.reservation.backend.dto.constants.ReservationStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDto {

    private Long reservationId; // 예약 아이디
    private UserDto user; // 예약 사용자
    private StoreDto store;// 상점
    private TimeTableDto timeTable; // 시간
    private ReservationStatus status; // 예약 상태

    public static ReservationDto fromEntity(Reservation reservation) {
        return ReservationDto.builder()
                .reservationId(reservation.getReservationId())
                .user(UserDto.fromEntity(reservation.getUser()))
                .store(StoreDto.fromEntity(reservation.getStore()))
                .timeTable(TimeTableDto.fromEntity(reservation.getTimeTable()))
                .status(reservation.getReservationStatus())
                .build();
    }
}
