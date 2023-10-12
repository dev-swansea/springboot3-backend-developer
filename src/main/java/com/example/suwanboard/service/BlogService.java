package com.example.suwanboard.service;

import com.example.suwanboard.domain.Article;
import com.example.suwanboard.dto.ArticleRequest;
import com.example.suwanboard.dto.UpdateArticleRequest;
import com.example.suwanboard.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

  private final BlogRepository blogRepository;

  public Article save(ArticleRequest articleRequest) {
    return blogRepository.save(articleRequest.toEntity());
  }

  public List<Article> findAll() {
    return blogRepository.findAll();
  }

  public Article findById(Long id) {
    return blogRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Not found:" + id));
  }

  public void delete(Long id) {
    blogRepository.deleteById(id);
  }

  @Transactional
  public Article update(Long id, UpdateArticleRequest request) {
    Article article = blogRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

    article.update(request.getTitle(), request.getContent());

    return article;
  }

}
