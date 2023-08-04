package com.daeun.reservation.backend.exception;

import com.daeun.reservation.backend.dto.constants.ErrorCode;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreReservationException extends RuntimeException{

    private ErrorCode errorCode;
    private String errorMessage;

    public StoreReservationException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
