/*
package com.example.suwanboard.config;

import com.example.suwanboard.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final UserDetailService userService;

  // 1) 시큐리티 기능 비활성화
  @Bean
  public WebSecurityCustomizer configure() {
    return (web) -> web.ignoring()
            .requestMatchers("/static/**");
  }

  // 2) 특정 HTTP 요청에 대한 웹 기반 보안 구성
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/login", "/signup", "/user").permitAll() // 3) 인증, 인가 설정
                    .anyRequest().authenticated())
            .formLogin(formLogin -> // 4) 폼 기반 로그인 설정
                    formLogin
                            .loginPage("/login")
                            .defaultSuccessUrl("/articles"))
            .logout(logout -> // 5) 로그아웃 설정
                    logout.logoutSuccessUrl("/login")
                            .invalidateHttpSession(true))
            .csrf(csrf ->
                    csrf.disable())
            .build();
  }

  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider() throws Exception {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

    daoAuthenticationProvider.setUserDetailsService(userService);
    daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());

    return daoAuthenticationProvider;
  }


  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
*/
