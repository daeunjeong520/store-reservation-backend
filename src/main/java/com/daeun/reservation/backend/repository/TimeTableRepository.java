package com.daeun.reservation.backend.repository;

import com.daeun.reservation.backend.domain.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface TimeTableRepository extends JpaRepository<TimeTable, Long> {

    Optional<TimeTable> findByStore_StoreIdAndDateAndStartedAndEnded(
            Long storeId, LocalDate date, LocalTime started, LocalTime ended
    );

    Optional<TimeTable> findByStartedAndEnded(LocalTime started, LocalTime ended);
    List<TimeTable> findAllByStore_StoreIdAndDate(Long storeId, LocalDate date);
}