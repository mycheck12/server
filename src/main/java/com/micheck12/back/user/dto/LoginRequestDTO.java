package com.micheck12.back.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class LoginRequestDTO {

  @NotBlank(message = "아이디는 필수 입력값입니다.")
  @Pattern(regexp = "^(?=.*[a-z])(?=.*[0-9])[a-z0-9]{6,12}$\n", message = "아이디는 영소문자, 숫자를 혼용해 6자 이상 12자 이하여야합니다.")
  private String username;

  @NotBlank(message = "비밀번호는 필수 입력값입니다.")
  @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d|.*[\\W_]).{6,12}$\n", message = "비밀번호는 영문자는 필수, 숫자와 특수문자 중 하나 이상 포함해 6자 이상 12자 이하여야합니다.")
  private String password;
}
