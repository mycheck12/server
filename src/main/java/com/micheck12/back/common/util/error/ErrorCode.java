package com.micheck12.back.common.util.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
  /* code: 400 */
  INVALID_REFRESH_TOKEN(BAD_REQUEST, "유효하지 않은 리프레시 토큰입니다."),

  /* code: 401 */
  INVALID_AUTH_TOKEN(UNAUTHORIZED, "권한 정보가 없는 토큰입니다."),
  INVALID_USERNAME(UNAUTHORIZED, "username이 올바르지 않습니다."),

  /* code: 404 */
  USER_NOT_FOUND(NOT_FOUND, "유저 정보가 존재하지 않습니다."),
  NOT_EXISTED_CHAT(NOT_FOUND, "존재하지 않는 방입니다."),
  ;

  private final HttpStatus httpStatus;
  private final String detail;
}
