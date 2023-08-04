package com.daeun.reservation.backend.repository;

import com.daeun.reservation.backend.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
