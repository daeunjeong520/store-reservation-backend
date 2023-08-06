package com.daeun.reservation.backend.repository;

import com.daeun.reservation.backend.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}