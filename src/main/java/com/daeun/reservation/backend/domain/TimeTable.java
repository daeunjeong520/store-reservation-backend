package com.daeun.reservation.backend.domain;

import com.daeun.reservation.backend.dto.TimeTableDto;
import com.daeun.reservation.backend.dto.constants.TimeTableStatus;
import lombok.*;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeTable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "time_table_id")
    private Long timeTableId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    private LocalDate date; // 날짜

    private LocalTime started; // 시작 시간
    private LocalTime ended; // 종료 시간

    @Setter
    @Enumerated(EnumType.STRING)
    private TimeTableStatus status; // 예약 여부

    public static TimeTable to(Store store, TimeTableDto timeTableDto) {
        return TimeTable.builder()
                .store(store)
                .date(timeTableDto.getDate())
                .started(timeTableDto.getStarted())
                .ended(timeTableDto.getEnded())
                .status(timeTableDto.getStatus())
                .build();
    }
}
