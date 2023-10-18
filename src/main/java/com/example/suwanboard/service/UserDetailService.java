package com.example.suwanboard.service;

import com.example.suwanboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService { // 시큐리티에서 사용자 정보를 가져오는 인터페이스

  private final UserRepository userRepository;

  // 사용자 이름(email)로 사용자의 정보를 가져오는 메서드
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return userRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException(email));
  }
}
