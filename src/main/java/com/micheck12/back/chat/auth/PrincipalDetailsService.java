package com.micheck12.back.chat.auth;

import com.micheck12.back.chat.entity.User;
import com.micheck12.back.chat.exception.CustomException;
import com.micheck12.back.chat.repository.UserRepository;
import com.micheck12.back.chat.util.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByUsername(username);

    if (user.isEmpty()) throw new CustomException(ErrorCode.USER_NOT_FOUND);

    return new PrincipalDetails(user.get());
  }
}
