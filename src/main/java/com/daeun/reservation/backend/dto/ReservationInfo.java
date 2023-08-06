package com.daeun.reservation.backend.dto;

import com.daeun.reservation.backend.dto.constants.ReservationStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationInfo {

    private Long reservationId;
    private UserDto user;
    private StoreDto store;
    private TimeTableDto timeTable;
    private ReservationStatus status;

    public static ReservationInfo from(ReservationDto reservationDto) {
        return ReservationInfo.builder()
                .reservationId(reservationDto.getReservationId())
                .user(reservationDto.getUser())
                .store(reservationDto.getStore())
                .timeTable(reservationDto.getTimeTable())
                .status(reservationDto.getStatus())
                .build();
    }
}
