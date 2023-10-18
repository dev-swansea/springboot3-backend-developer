package com.example.suwanboard.dto;

import com.example.suwanboard.domain.Article;
import lombok.Getter;

@Getter
public class ArticleListViewResponse {

  private final Long id;
  private final String title;
  private final String content;

  public ArticleListViewResponse(Article article) {
    this.id = article.getId();
    this.title = article.getTitle();
    this.content = article.getContent();
  }
}
