package com.daeun.reservation.backend.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationCheckInfo {

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
}
