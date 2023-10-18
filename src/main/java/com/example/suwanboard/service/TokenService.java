package com.example.suwanboard.service;

import com.example.suwanboard.config.jwt.TokenProvider;
import com.example.suwanboard.domain.RefreshToken;
import com.example.suwanboard.domain.User;
import com.example.suwanboard.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenService {

  private final TokenProvider tokenProvider;
  private final RefreshTokenService refreshTokenService;
  private final UserService userService;

  public String createNewAccessToken(String refreshToken) {
    // 토큰 유효성 검사에 실패하면 예외 발생
    if (!tokenProvider.validToken(refreshToken)) {
      throw new IllegalArgumentException("Unexpected token");
    }

    Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
    User user = userService.findById(userId);

    return tokenProvider.generateToken(user, Duration.ofHours(2));
  }

}
