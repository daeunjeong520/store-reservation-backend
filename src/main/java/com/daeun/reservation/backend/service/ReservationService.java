package com.daeun.reservation.backend.service;

import com.daeun.reservation.backend.domain.Reservation;
import com.daeun.reservation.backend.domain.Store;
import com.daeun.reservation.backend.domain.User;
import com.daeun.reservation.backend.dto.ReservationDto;
import com.daeun.reservation.backend.dto.constants.ErrorCode;
import com.daeun.reservation.backend.dto.constants.ReservationStatus;
import com.daeun.reservation.backend.exception.StoreReservationException;
import com.daeun.reservation.backend.repository.ReservationRepository;
import com.daeun.reservation.backend.repository.StoreRepository;
import com.daeun.reservation.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    // 예약 등록
    public ReservationDto registerReservation(Long storeId, LocalDateTime started, LocalDateTime ended) {
        // 사용자 확인
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User findUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new StoreReservationException(ErrorCode.USER_NOT_FOUND));
        
        // 매장 확인
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreReservationException(ErrorCode.STORE_NOT_FOUND));

        // 예약 진행
        Reservation reservation = Reservation.builder()
                .user(findUser)
                .store(store)
                .started(started)
                .ended(ended)
                .status(ReservationStatus.REGISTERED)
                .build();

        return ReservationDto.from(reservationRepository.save(reservation));
    }

    // 예약 확인(10분전 방문)

}
