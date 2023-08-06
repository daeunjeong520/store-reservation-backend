package com.daeun.reservation.backend.dto;

import com.daeun.reservation.backend.dto.constants.TimeTableStatus;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateTimeTable {

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
        private Long storeId;

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate date;

        @DateTimeFormat(pattern = "HH:mm:ss")
        private LocalTime started;

        @DateTimeFormat(pattern = "HH:mm:ss")
        private LocalTime ended;

        private TimeTableStatus status;

        public static Response from(TimeTableDto timeTableDto) {
            return Response.builder()
                    .storeId(timeTableDto.getStoreId())
                    .date(timeTableDto.getDate())
                    .started(timeTableDto.getStarted())
                    .ended(timeTableDto.getEnded())
                    .status(timeTableDto.getStatus())
                    .build();
        }
    }
}
