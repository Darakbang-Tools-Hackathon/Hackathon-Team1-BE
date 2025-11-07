package com.springboot.hackathonteam1be.core.service;

import com.springboot.hackathonteam1be.api.dto.SignUpRequestDto;
import com.springboot.hackathonteam1be.api.dto.TokenResponseDto;
import com.springboot.hackathonteam1be.config.JwtTokenProvider;
import com.springboot.hackathonteam1be.api.dto.LoginRequestDto;
import com.springboot.hackathonteam1be.core.domain.UserEntity;
import com.springboot.hackathonteam1be.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final JwtTokenProvider jwtTokenProvider;

  @Transactional
  public void signUp(SignUpRequestDto signUpRequestDto) {
    // 이메일 중복 확인
    userRepository.findByEmail(signUpRequestDto.getEmail())
        .ifPresent(user -> {
          throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        });

    // 사용자 엔티티 생성 및 저장
    UserEntity newUser = UserEntity.builder()
        .email(signUpRequestDto.getEmail())
        .name(signUpRequestDto.getName())
        .build();

        userRepository.save(newUser);
      }

      @Transactional(readOnly = true)
  public TokenResponseDto login(LoginRequestDto loginRequestDto) {
    UserEntity user = userRepository.findByEmail(loginRequestDto.getEmail())
        .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 이메일입니다."));
        if(!loginRequestDto.getPassword().equals(user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        String accessToken = jwtTokenProvider.createToken(user.getEmail());
        return new TokenResponseDto(accessToken);
  }
}
