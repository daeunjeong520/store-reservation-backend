package com.daeun.reservation.backend.service;

import com.daeun.reservation.backend.domain.Store;
import com.daeun.reservation.backend.domain.TimeTable;
import com.daeun.reservation.backend.domain.User;
import com.daeun.reservation.backend.dto.StoreDto;
import com.daeun.reservation.backend.dto.TimeTableDto;
import com.daeun.reservation.backend.dto.constants.ErrorCode;
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

    // 상점등록
    @Transactional
    public StoreDto registerStore(String name, String location, String description) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User findUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new StoreReservationException(ErrorCode.USER_NOT_FOUND));

        Store store = Store.builder()
                .user(findUser)
                .name(name)
                .location(location)
                .description(description)
                .build();

        // 상점 등록
        return StoreDto.fromEntity(storeRepository.save(store));
    }

    // 상점 리스트 조회(기본)
    public List<StoreDto> getStores() {
        return storeRepository.findAll()
                .stream()
                .map(StoreDto::fromEntity)
                .collect(Collectors.toList());
    }

    // 상점 리스트 조회(가나다순)
    public List<StoreDto> getStoresOrderByName() {
        return storeRepository.findAll(Sort.by(Sort.Direction.ASC, "name"))
                .stream()
                .map(StoreDto::fromEntity)
                .collect(Collectors.toList());
    }

    // 상점 예약 테이블 저장
    @Transactional
    public List<TimeTableDto> createTimeTable(Long storeId, List<TimeTableDto> timeTableDtos) {
        // 상점 여부 확인
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreReservationException(ErrorCode.STORE_NOT_FOUND));

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

    // 상점 예약 테이블 조회(사용 가능한 예약 테이블만 제공)
    public List<TimeTableDto> getTimeTableByDate(Long storeId, LocalDate date) {
        // 상점 조회
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreReservationException(ErrorCode.STORE_NOT_FOUND));

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

    // 상점 상세조회

    
}
