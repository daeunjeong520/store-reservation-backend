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
    STORE_NOT_FOUND("상점이 존재하지 않습니다")
    ;

    private final String description;
}
