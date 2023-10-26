package com.micheck12.back.chat.exception;

import com.micheck12.back.chat.util.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {
  private final ErrorCode errorCode;
}
