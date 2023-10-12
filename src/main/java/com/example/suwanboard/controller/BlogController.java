package com.example.suwanboard.controller;

import com.example.suwanboard.domain.Article;
import com.example.suwanboard.dto.ArticleRequest;
import com.example.suwanboard.dto.ArticleResponse;
import com.example.suwanboard.dto.UpdateArticleRequest;
import com.example.suwanboard.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor // HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
public class BlogController {

  private final BlogService blogService;

  @PostMapping("/api/articles")
  public ResponseEntity<Article> addArticle(@RequestBody ArticleRequest articleRequest) { // 요청 본문 매핑
    Article saveArticle = blogService.save(articleRequest);
    //return ResponseEntity.status(HttpStatus.CREATED)
    //        .body(saveArticle);
    return new ResponseEntity<>(saveArticle, HttpStatus.CREATED);
  }

  @GetMapping("/api/articles")
  public ResponseEntity<List<ArticleResponse>> findAllArticles() {
    List<ArticleResponse> articles = blogService.findAll()
            .stream()
            .map(ArticleResponse::new)
            .toList();
    return ResponseEntity.ok().body(articles);
  }

  @GetMapping("/api/articles/{id}")
  public ResponseEntity<ArticleResponse> findArticle(@PathVariable Long id) {
    Article article = blogService.findById(id);
    return ResponseEntity.ok().body(new ArticleResponse(article));
  }

  @DeleteMapping("/api/articles/{id}")
  public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
    blogService.delete(id);

    return ResponseEntity.ok().build();
  }

  @PutMapping("/api/articles/{id}")
  public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody UpdateArticleRequest request) {
    Article updateArticle = blogService.update(id, request);
    return ResponseEntity.ok()
            .body(updateArticle);
  }
}
