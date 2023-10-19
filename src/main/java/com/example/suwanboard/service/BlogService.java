package com.example.suwanboard.service;

import com.example.suwanboard.domain.Article;
import com.example.suwanboard.dto.ArticleRequest;
import com.example.suwanboard.dto.UpdateArticleRequest;
import com.example.suwanboard.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

  private final BlogRepository blogRepository;

  public Article save(ArticleRequest articleRequest, String userName) {
    return blogRepository.save(articleRequest.toEntity(userName));
  }

  public List<Article> findAll() {
    return blogRepository.findAll();
  }

  public Article findById(Long id) {
    return blogRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Not found:" + id));
  }

  public void delete(Long id) {
    Article article = blogRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    authorizeArticleAuthor(article);
    blogRepository.deleteById(id);
  }

  @Transactional
  public Article update(Long id, UpdateArticleRequest request) {
    Article article = blogRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    authorizeArticleAuthor(article);
    article.update(request.getTitle(), request.getContent());

    return article;
  }

  // 게시글 작성자 유저인지 확인
  private static void authorizeArticleAuthor(Article article) {
    String userName = SecurityContextHolder.getContext().getAuthentication().getName();
    if (!article.getAuthor().equals(userName)) {
      throw new IllegalArgumentException("not authorized");
    }
  }


}
