package com.daeun.reservation.backend.repository;

import com.daeun.reservation.backend.domain.Reservation;
import com.daeun.reservation.backend.dto.constants.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByUser_UserIdAndStore_StoreId(Long userId, Long storeId);
    List<Reservation> findAllByUser_UserIdAndStore_StoreIdAndReservationStatus(
            Long userId, Long storeId, ReservationStatus status);
}
