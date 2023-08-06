package com.daeun.reservation.backend.dto;

import com.daeun.reservation.backend.dto.constants.TimeTableStatus;
import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeTableByDate {

    private LocalTime started;
    private LocalTime ended;
    private TimeTableStatus status;

    public static TimeTableByDate from(TimeTableDto timeTableDto) {
        return TimeTableByDate.builder()
                .started(timeTableDto.getStarted())
                .ended(timeTableDto.getEnded())
                .status(timeTableDto.getStatus())
                .build();
    }
}
