package com.example.suwanboard.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

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

  @CreatedDate // 엔티티가 생성될 때 생성 시간 저장
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @LastModifiedDate // 엔티티가 수정될 때 수정 시간 저장
  @Column(name = "updated_at")
  private LocalDateTime updateAt;

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
