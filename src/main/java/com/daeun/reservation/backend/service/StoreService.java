package com.daeun.reservation.backend.service;

import com.daeun.reservation.backend.domain.Store;
import com.daeun.reservation.backend.domain.TimeTable;
import com.daeun.reservation.backend.domain.User;
import com.daeun.reservation.backend.dto.StoreDto;
import com.daeun.reservation.backend.dto.TimeTableDto;
import com.daeun.reservation.backend.dto.constants.ErrorCode;
import com.daeun.reservation.backend.dto.constants.ApprovalStatus;
import com.daeun.reservation.backend.dto.constants.TimeTableStatus;
import com.daeun.reservation.backend.exception.StoreReservationException;
import com.daeun.reservation.backend.repository.StoreRepository;
import com.daeun.reservation.backend.repository.TimeTableRepository;
import com.daeun.reservation.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreService {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final TimeTableRepository timeTableRepository;

    /**
     * 상점 등록
     */
    @Transactional
    public StoreDto registerStore(String name, String location, String description) {

        // 유저 확인
        User user = validateUser();

        Store store = Store.builder()
                .user(user)
                .name(name)
                .location(location)
                .description(description)
                .build();

        // 상점 등록
        return StoreDto.fromEntity(storeRepository.save(store));
    }

    /**
     * 상점 상세 조회
     */
    public StoreDto getStore(Long storeId) {
        return StoreDto.fromEntity(validateStore(storeId));
    }

    /**
     * 상점 리스트 조회(기본)
     */
    public List<StoreDto> getStores() {
        return storeRepository.findAll()
                .stream()
                .map(StoreDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 상점 리스트 조회(가나다순)
     */
    public List<StoreDto> getStoresOrderByName() {
        return storeRepository.findAll(Sort.by(Sort.Direction.ASC, "name"))
                .stream()
                .map(StoreDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 상점 예약 테이블 저장
     */
    @Transactional
    public List<TimeTableDto> createTimeTable(Long storeId, List<TimeTableDto> timeTableDtos) {
        // 상점 확인
        Store store = validateStore(storeId);

        List<TimeTable> timeTables = timeTableDtos.stream()
                .map(timeTableDto -> TimeTable.to(store, timeTableDto))
                .collect(Collectors.toList());

        // 이미 등록된 예약 테이블 시간이 있는지 체크(시작시간, 끝나는시간)
        for(TimeTable timeTable: timeTables) {
            timeTableRepository.findByStartedAndEnded(timeTable.getStarted(), timeTable.getEnded())
                    .ifPresent(it -> {
                        throw new StoreReservationException(ErrorCode.ALREADY_REGISTER_TIMETABLE);
                    });
        }

        // 상점에 예약 테이블 저장
        List<TimeTableDto> savedTimeTableDtos = new ArrayList<>();
        for(TimeTable timeTable: timeTables) {
            savedTimeTableDtos.add(
                    TimeTableDto.fromEntity(timeTableRepository.save(timeTable))
            );
        }
        return savedTimeTableDtos;
    }

    /**
     * 상점 예약 테이블 조회(사용 가능한 예약 테이블만 제공)
     */
    public List<TimeTableDto> getTimeTableByDate(Long storeId, LocalDate date) {
        // 상점 확인
        Store store = validateStore(storeId);

        // 해당 날짜의 예약 테이블 시간 조회(Timetable 의 상태 -> before_use 만)
        List<TimeTableDto> timeTableDtos = new ArrayList<>();
        List<TimeTable> timeTables = timeTableRepository.findAllByStore_StoreIdAndDate(storeId, date);

        for(TimeTable timeTable: timeTables) {
            if(timeTable.getStatus() == TimeTableStatus.BEFORE_USE) {
                timeTableDtos.add(TimeTableDto.fromEntity(timeTable));
            }
        }
        return timeTableDtos;
    }

    /**
     * 점주 예약 확인 후 승인/거절
     */
    public ApprovalStatus getApprovalStatus(Long storeId, LocalDate date, LocalTime started, LocalTime ended, Boolean status) {
        // 상점 확인
        validateStore(storeId);

        // 예약 시간 확인
        timeTableRepository.findByStore_StoreIdAndDateAndStartedAndEnded(storeId, date, started, ended)
                .orElseThrow(() -> new StoreReservationException(ErrorCode.TIMETABLE_NOT_FOUND));

        // 점주 요청 상태(status -> true/false)
        if(!status) {
            return ApprovalStatus.REFUSAL;
        }
        return ApprovalStatus.APPROVAL;
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
