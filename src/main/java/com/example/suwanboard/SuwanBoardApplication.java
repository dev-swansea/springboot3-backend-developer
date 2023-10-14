package com.example.suwanboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // created_at, updated_at 자동 업데이트
@SpringBootApplication
public class SuwanBoardApplication {

  public static void main(String[] args) {
    SpringApplication.run(SuwanBoardApplication.class, args);
  }

}
