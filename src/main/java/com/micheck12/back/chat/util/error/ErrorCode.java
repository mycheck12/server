package com.micheck12.back.chat.util.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
  NOT_EXISTED_CHAT(HttpStatus.BAD_REQUEST, "존재하지 않는 방입니다.")
  ;

  private final HttpStatus httpStatus;
  private final String detail;
}
