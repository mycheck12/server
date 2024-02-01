package com.micheck12.back.user.config.JWT;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/* 유요한 자격 증명을 제공하지 않고 접근하려할 때 401 Unauthorized 에러 리턴 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
  }
}
