package com.daeun.reservation.backend.controller;

import com.daeun.reservation.backend.dto.*;
import com.daeun.reservation.backend.dto.constants.ApprovalStatus;
import com.daeun.reservation.backend.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    // 상점 등록
    @PostMapping
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public RegisterStore.Response registerStore(
            @RequestBody @Valid RegisterStore.Request storeRequest
    ) {
        return RegisterStore.Response.from(
                storeService.registerStore(
                        storeRequest.getName(),
                        storeRequest.getLocation(),
                        storeRequest.getDescription()
                )
        );
    }

    // 상점 상세조회
    @GetMapping("/{storeId}")
    public StoreInfo getStore(
            @PathVariable Long storeId
    ) {
        return StoreInfo.from(
                storeService.getStore(storeId)
        );
    }

    // 상점 리스트 조회(기본, 가나다순)
    @GetMapping
    public List<StoreInfo> getStores(
            @RequestParam(required = false) Boolean isOrder
    ) {
        List<StoreDto> storeDtos;
        if(isOrder == null) {
               storeDtos = storeService.getStores();
        }else {
               storeDtos = storeService.getStoresOrderByName();
        }

       return storeDtos.stream()
               .map(StoreInfo::from)
               .collect(Collectors.toList());
    }

    // 상점에 예약 테이블 구성
    @PostMapping("/{storeId}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public List<CreateTimeTable.Response> createTimeTable(
            @RequestBody @Valid List<CreateTimeTable.Request> requests,
            @PathVariable Long storeId
    ) {
        List<TimeTableDto> timeTableDtos = requests.stream().map(TimeTableDto::fromRequest)
                .collect(Collectors.toList());

        return storeService.createTimeTable(storeId, timeTableDtos)
                .stream()
                .map(CreateTimeTable.Response::from)
                .collect(Collectors.toList());
    }

    // 날짜 별 시간 테이블 목록
    @GetMapping("/{storeId}/timetable")
    public List<TimeTableByDate> getTimeTableByDate(
            @PathVariable Long storeId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ) {
        return storeService.getTimeTableByDate(storeId, date)
                .stream()
                .map(TimeTableByDate::from)
                .collect(Collectors.toList());
    }

    // 점주 예약 승인/거절
    // localhost:8080/stores/1/approval?status=true (승인/거절)
    @GetMapping("/{storeId}/approval")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ApprovalStatus getApprovalStatus(
            @RequestBody @Valid RegisterReservation.Request request,
            @PathVariable Long storeId,
            @RequestParam Boolean status
    ) {
        return storeService.getApprovalStatus(
                storeId,
                request.getDate(),
                request.getStarted(),
                request.getEnded(),
                status
        );
    }
}
