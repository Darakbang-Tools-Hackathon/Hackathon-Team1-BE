package com.springboot.hackathonteam1be.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor; 

@Getter
@Setter
@NoArgsConstructor
public class SignUpRequestDto {
  @NotEmpty(message = "이메일은 필수 항목입니다.")
  @Email(message = "올바른 이메일 형식이 아닙니다.")
  private String email;

  @NotEmpty(message = "비밀번호는 필수 항목입니다.")
  @Size(min=8, message = "비밀번호는 8자 이상이어야 합니다.")
  private String password;

  @NotEmpty(message = "이름은 필수 항목입니다.")
  private String name;
}
