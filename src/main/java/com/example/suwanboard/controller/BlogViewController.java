package com.example.suwanboard.controller;

import com.example.suwanboard.domain.Article;
import com.example.suwanboard.dto.ArticleListViewResponse;
import com.example.suwanboard.dto.ArticleViewResponse;
import com.example.suwanboard.service.BlogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BlogViewController {

  private final BlogService blogService;

  @GetMapping("/articles")
  public String getArticles(Model model) {
    List<ArticleListViewResponse> articles = blogService.findAll().stream()
            .map(ArticleListViewResponse::new)
            .toList();

    model.addAttribute("articles", articles); // 블로그 글 리스트를 model에 저장

    return "article-list"; // article-list.html 페이지 찾기
  }

  @GetMapping("/articles/{id}")
  public String getArticle(@PathVariable Long id, Model model) {
    Article article = blogService.findById(id);
    model.addAttribute("article", article);
    return "article";
  }

  @GetMapping("/new-article")
  // id라는 키를 가진 쿼리 파라미터 값을 가져오는데, id는 없을 수도 있다... 이렇게 없을때 예외를 피하는구나??
  public String newArticle(@RequestParam(required = false) Long id, Model model) {
    if (id == null) { // id가 없으면 생성
      model.addAttribute("article", new ArticleViewResponse());
    } else {
      Article article = blogService.findById(id);
      model.addAttribute("article", new ArticleViewResponse((article)));
    }
    return "new-article";
  }
}
