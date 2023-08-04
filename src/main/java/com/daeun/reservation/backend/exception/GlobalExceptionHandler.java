package com.daeun.reservation.backend.exception;

import com.daeun.reservation.backend.dto.ErrorResponse;
import com.daeun.reservation.backend.dto.constants.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.daeun.reservation.backend.dto.constants.ErrorCode.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StoreReservationException.class)
    public ErrorResponse handleStoreReservationException(StoreReservationException e) {
        log.error("{} is occurred.", e.getErrorCode());
        return new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ErrorResponse handleUsernameNotFoundException(UsernameNotFoundException e) {
        log.error("UsernameNotFoundException is occurred.", e);
        return new ErrorResponse(USER_NOT_FOUND, USER_NOT_FOUND.getDescription());
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception e) {
        log.error("Exception is occurred.", e);
        return new ErrorResponse(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR.getDescription());
    }
}
