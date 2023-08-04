package com.daeun.reservation.backend.dto;

import com.daeun.reservation.backend.domain.User;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long userId;
    private String username;
    private String password;
    private List<String> roles;

    public static UserDto fromEntity(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles())
                .build();
    }
}
