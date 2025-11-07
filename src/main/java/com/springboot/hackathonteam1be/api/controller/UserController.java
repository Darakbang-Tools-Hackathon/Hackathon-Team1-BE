package com.springboot.hackathonteam1be.api.controller;

import com.springboot.hackathonteam1be.api.dto.TokenResponseDto;
import com.springboot.hackathonteam1be.api.dto.LoginRequestDto;
import com.springboot.hackathonteam1be.api.dto.SignUpRequestDto;
import com.springboot.hackathonteam1be.core.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
  private final UserService userService;

  @PostMapping("/signup")
  public ResponseEntity<String> signup(@RequestBody SignUpRequestDto signupRequestDto) {
    userService.signUp(signupRequestDto);
      return ResponseEntity.ok("회원가입 성공");
  }

  @PostMapping("/login")
  public ResponseEntity<TokenResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
    TokenResponseDto token = userService.login(loginRequestDto);
    return ResponseEntity.ok(token);
  }
}
