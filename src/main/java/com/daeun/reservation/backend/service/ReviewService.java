package com.daeun.reservation.backend.service;

import com.daeun.reservation.backend.domain.Reservation;
import com.daeun.reservation.backend.domain.Review;
import com.daeun.reservation.backend.domain.Store;
import com.daeun.reservation.backend.domain.User;
import com.daeun.reservation.backend.dto.ReviewDto;
import com.daeun.reservation.backend.dto.constants.ErrorCode;
import com.daeun.reservation.backend.dto.constants.Rating;
import com.daeun.reservation.backend.dto.constants.ReservationStatus;
import com.daeun.reservation.backend.exception.StoreReservationException;
import com.daeun.reservation.backend.repository.ReservationRepository;
import com.daeun.reservation.backend.repository.ReviewRepository;
import com.daeun.reservation.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewRepository;

    /**
     * 리뷰 저장
     */
    @Transactional
    public ReviewDto createReview(Long reservationId, String title, String content, Rating rating) {
        // 사용자 확인
        User user = validateUser();

        // 예약 사용 여부
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new StoreReservationException(ErrorCode.RESERVATION_NOT_FOUND));

        if(reservation.getReservationStatus() == ReservationStatus.REGISTRATION_COMP) {
            throw new StoreReservationException(ErrorCode.RESERVATION_ALREADY_USE);
        }
        if(reservation.getReservationStatus() == ReservationStatus.CANCEL) {
            throw new StoreReservationException(ErrorCode.RESERVATION_CANCELED);
        }

        Store store = reservation.getStore();

        // 리뷰 등록
        return ReviewDto.fromEntity(reviewRepository.save(Review.builder()
                .user(user)
                .store(store)
                .title(title)
                .content(content)
                .rating(rating)
                .build()));
    }

    // 유저 확인
    private User validateUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new StoreReservationException(ErrorCode.USER_NOT_FOUND));
    }
}
