package com.example.suwanboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan
public class SuwanBoardApplication {

  public static void main(String[] args) {
    SpringApplication.run(SuwanBoardApplication.class, args);
  }

}
