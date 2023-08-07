package com.daeun.reservation.backend.dto;

import com.daeun.reservation.backend.dto.constants.ReservationStatus;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class RegisterReservation {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request {

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate date;

        @DateTimeFormat(pattern = "HH:mm:ss")
        private LocalTime started;

        @DateTimeFormat(pattern = "HH:mm:ss")
        private LocalTime ended;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {

        private Long reservationId; // 예약 아이디
        private String username; // 예약 사용자 이름
        private String storeName; // 상점 이름
        private TimeTableDto timeTable; // 예약 시간 정보
        private ReservationStatus status; // 예약 상태

        public static Response from(ReservationDto reservationDto) {
            return Response.builder()
                    .reservationId(reservationDto.getReservationId())
                    .username(reservationDto.getUser().getUsername())
                    .storeName(reservationDto.getStore().getStoreName())
                    .timeTable(reservationDto.getTimeTable())
                    .status(reservationDto.getStatus())
                    .build();
        }
    }
}
