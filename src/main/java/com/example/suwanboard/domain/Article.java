package com.example.suwanboard.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Article {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 자동으로 1씩 증가
  @Column(name = "id", updatable = false)
  private Long id;

  @Column(name = "title", nullable = false) // title 이라는 not null 컬럼과 매핑
  private String title;

  @Column(name = "content", nullable = false)
  private String content;

  @Builder // 빌더 패턴으로 객체 생성
  public Article(String title, String content) {
    this.title = title;
    this.content = content;
  }

  public void update(String title, String content) {
    this.title = title;
    this.content = content;
  }

}