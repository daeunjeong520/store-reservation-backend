package com.daeun.reservation.backend.dto;

import com.daeun.reservation.backend.dto.constants.ErrorCode;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {

    private ErrorCode errorCode;
    private String errorMessage;
}
