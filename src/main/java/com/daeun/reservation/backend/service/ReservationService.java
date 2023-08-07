package com.daeun.reservation.backend.service;

import com.daeun.reservation.backend.domain.Reservation;
import com.daeun.reservation.backend.domain.Store;
import com.daeun.reservation.backend.domain.TimeTable;
import com.daeun.reservation.backend.domain.User;
import com.daeun.reservation.backend.dto.ReservationDto;
import com.daeun.reservation.backend.dto.constants.*;
import com.daeun.reservation.backend.exception.StoreReservationException;
import com.daeun.reservation.backend.repository.ReservationRepository;
import com.daeun.reservation.backend.repository.StoreRepository;
import com.daeun.reservation.backend.repository.TimeTableRepository;
import com.daeun.reservation.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final TimeTableRepository timeTableRepository;

    /**
     * 예약 등록
     */
    @Transactional
    public ReservationDto registerReservation(Long storeId, LocalDate date, LocalTime started, LocalTime ended, ApprovalStatus approvalStatus) {
        // 사용자 확인
        User user = validateUser();

        // 상점 확인
        Store store = validateStore(storeId);

        // 예약 시간 확인
        TimeTable timeTable = timeTableRepository.findByStore_StoreIdAndDateAndStartedAndEnded(storeId, date, started, ended)
                .orElseThrow(() -> new StoreReservationException(ErrorCode.TIMETABLE_NOT_FOUND));

        // 해당 예약 시간 이미 사용중이면 에러 (예약 가능 여부 체크)
        if(timeTable.getStatus() == TimeTableStatus.USE) {
            throw new StoreReservationException(ErrorCode.TIMETABLE_ALREADY_USE);
        }

        // 점주의 요청 상태로 예약 승인/거절
        if(approvalStatus == ApprovalStatus.REFUSAL) {
           throw new StoreReservationException(ErrorCode.RESERVATION_REFUSAL);
        }

        // 해당 예약 시간 사용 상태로 변경(BEFORE_USE -> USE)
        timeTable.setStatus(TimeTableStatus.USE);

        // 예약 등록
        return ReservationDto.fromEntity(
                reservationRepository.save(
                        Reservation.builder()
                                .user(user)
                                .store(store)
                                .timeTable(timeTable)
                                .reservationStatus(ReservationStatus.REGISTRATION_COMP) // 예약 등록 완료
                                .build()
                )
        );
    }

    /**
     * 예약 시간 도착(예약 사용) (10분전 도착하지 않으면 -> 예약 취소)
     */
    @Transactional
    public ReservationDto useReservation(Long reservationId, LocalTime arrivedAt) {
        // 사용자 확인
        User user = validateUser();

        // 예약 여부 확인
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new StoreReservationException(ErrorCode.RESERVATION_NOT_FOUND));

        // 예약 상태가 취소되었거나 사용 완료되었으면 예약 사용 불가능
        if(reservation.getReservationStatus() == ReservationStatus.CANCEL) {
            throw new StoreReservationException(ErrorCode.RESERVATION_CANCELED);
        }

        if(reservation.getReservationStatus() == ReservationStatus.USE_COM) {
            throw new StoreReservationException(ErrorCode.RESERVATION_ALREADY_USE);
        }

        TimeTable timeTable = reservation.getTimeTable();
        LocalTime started = timeTable.getStarted();

        // 도착시간이 예약 시간 10분 이내인지 확인 (예약시간 - 10분)
        if(arrivedAt.isAfter(started.minusMinutes(10))) {
            reservation.setReservationStatus(ReservationStatus.CANCEL); // 예약 취소
            throw new StoreReservationException(ErrorCode.NOT_ARRIVED_BEFORE_TEN_MINUTES);
        }

        // 예약 사용
        reservation.setReservationStatus(ReservationStatus.USE_COM); // update
        return ReservationDto.fromEntity(reservation);
    }

    /**
     * 사용자 예약 목록 조회(등록완료, 사용완료, 취소)
     */
    public List<ReservationDto> getReservationList(Long storeId) {
        // 유저 확인
        User user = validateUser();

        // 상점 확인
        Store store = validateStore(storeId);

        // 예약 목록 조회
        List<Reservation> reservationList = reservationRepository.findAllByUser_UserIdAndStore_StoreId(user.getUserId(), storeId);
        if(reservationList.size() == 0) {
            throw new StoreReservationException(ErrorCode.NOT_FOUND_RESERVATION_LIST);
        }

        return reservationList.stream()
                .map(ReservationDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 사용자 예약 목록 조회(사용 완료만 - 리뷰 작성을 위한 목록)
     */
    public List<ReservationDto> getUsedReservationList(Long storeId) {
        // 유저 확인
        User user = validateUser();

        // 상점 확인
        Store store = validateStore(storeId);

        // 예약 목록 조회
        return  reservationRepository.findAllByUser_UserIdAndStore_StoreIdAndReservationStatus(
                    user.getUserId(), storeId, ReservationStatus.USE_COM
                )
                .stream()
                .map(ReservationDto::fromEntity)
                .collect(Collectors.toList());
    }

    // 유저 확인
    private User validateUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new StoreReservationException(ErrorCode.USER_NOT_FOUND));
    }

    // 상점 확인
    private Store validateStore(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreReservationException(ErrorCode.STORE_NOT_FOUND));
    }
}