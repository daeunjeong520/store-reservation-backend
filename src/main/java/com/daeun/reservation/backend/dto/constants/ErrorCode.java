package com.daeun.reservation.backend.dto.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USER_NOT_FOUND("사용자가 없습니다"),
    DUPLICATE_USERNAME("이미 존재하는 회원입니다"),
    INTERNAL_SERVER_ERROR("내부 서버 오류가 발생했습니다"),
    INVALID_PASSWORD("비밀번호가 일치하지 않습니다"),
    STORE_NOT_FOUND("상점이 존재하지 않습니다"),
    TIMETABLE_NOT_FOUND("해당 상점의 예약 시간이 존재하지 않습니다"),
    ALREADY_REGISTER_TIMETABLE("이미 등록된 예약시간이 있습니다"),
    TIMETABLE_ALREADY_USE("해당 시간은 이미 예약되었습니다"),
    RESERVATION_NOT_FOUND("예약이 존재하지 않습니다"),
    RESERVATION_CANCELED("해당 예약은 취소된 예약입니다"),
    RESERVATION_ALREADY_USE("해당 예약은 이미 사용된 예약입니다"),
    NOT_ARRIVED_BEFORE_TEN_MINUTES("예약된 시간 10분 전 도착해야 합니다"),
    NOT_FOUND_RESERVATION_LIST("예약 목록이 존재하지 않습니다"),
    ;

    private final String description;
}
