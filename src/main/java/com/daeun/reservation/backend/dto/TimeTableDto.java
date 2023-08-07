package com.daeun.reservation.backend.dto;

import com.daeun.reservation.backend.domain.TimeTable;
import com.daeun.reservation.backend.dto.constants.TimeTableStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeTableDto {

    private Long storeId;
    private LocalDate date;
    private LocalTime started;
    private LocalTime ended;
    private TimeTableStatus status;

    public static TimeTableDto fromRequest(CreateTimeTable.Request request) {
        return TimeTableDto.builder()
                .date(request.getDate())
                .started(request.getStarted())
                .ended(request.getEnded())
                .status(TimeTableStatus.BEFORE_USE)
                .build();
    }

    public static TimeTableDto fromEntity(TimeTable timeTable) {
        return TimeTableDto.builder()
                .storeId(timeTable.getStore().getStoreId())
                .date(timeTable.getDate())
                .started(timeTable.getStarted())
                .ended(timeTable.getEnded())
                .status(timeTable.getStatus())
                .build();
    }
}
