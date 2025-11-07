package com.springboot.hackathonteam1be.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequestDto {
  @Email(message = "올바른 이메일 형식이 아닙니다.")
  @NotEmpty(message = "이메일은 필수 항목입니다.")
  private String email;
  @NotEmpty(message = "비밀번호는 필수 항목입니다.")
  private String password;
}
