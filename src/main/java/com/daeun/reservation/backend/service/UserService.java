package com.daeun.reservation.backend.service;

import com.daeun.reservation.backend.domain.User;
import com.daeun.reservation.backend.dto.UserDto;
import com.daeun.reservation.backend.dto.constants.ErrorCode;
import com.daeun.reservation.backend.exception.StoreReservationException;
import com.daeun.reservation.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.daeun.reservation.backend.dto.constants.ErrorCode.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new StoreReservationException(USER_NOT_FOUND));
    }

    // 회원가입
    @Transactional
    public UserDto signup(String username, String password, List<String> roles) {
        // 이미 존재하는 회원인지
        boolean exists = userRepository.existsByUsername(username);
        if(exists) {
            throw new StoreReservationException(DUPLICATE_USERNAME);
        }

        // 비밀번호 암호화, 회원 저장
        User user = User.builder()
                .username(username)
                .password(encoder.encode(password))
                .roles(roles)
                .build();

        return UserDto.fromEntity(userRepository.save(user));
    }

    // 로그인
    @Transactional
    public UserDto signin(String username, String password) {
        // 패스워드 검증
        User findUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new StoreReservationException(USER_NOT_FOUND));

        if(!encoder.matches(password, findUser.getPassword())) {
            throw new StoreReservationException(INVALID_PASSWORD);
        }
        return UserDto.fromEntity(findUser);
    }
}
