package com.daeun.reservation.backend.controller;

import com.daeun.reservation.backend.dto.SignIn;
import com.daeun.reservation.backend.dto.SignUp;
import com.daeun.reservation.backend.dto.UserDto;
import com.daeun.reservation.backend.security.TokenProvider;
import com.daeun.reservation.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final TokenProvider tokenProvider;

    // 회원가입
    @PostMapping("/signup")
    public SignUp.Response signup(@RequestBody SignUp.Request request) {

        log.info("username={}", request.getUsername());
        log.info("roles={}", request.getRoles());

        UserDto userDto = userService.signup(
                request.getUsername(),
                request.getPassword(),
                request.getRoles());

        return SignUp.Response.from(userDto);
    }

    // 로그인
    @PostMapping("/signin")
    public SignIn.Response signin(@RequestBody SignIn.Request request) {
        UserDto userDto = userService.signin(
                request.getUsername(),
                request.getPassword()
        );
        String token = tokenProvider.generateToken(userDto.getUsername(), userDto.getRoles());
        return new SignIn.Response(token);
    }
}
