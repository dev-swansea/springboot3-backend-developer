package com.example.suwanboard.controller;

import com.example.suwanboard.domain.Article;
import com.example.suwanboard.domain.User;
import com.example.suwanboard.dto.ArticleRequest;
import com.example.suwanboard.dto.UpdateArticleRequest;
import com.example.suwanboard.repository.BlogRepository;
import com.example.suwanboard.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // 테스트용 애플리케이션 컨텍스트
@AutoConfigureMockMvc // MockMvc 생성
class BlogControllerTest {

  @Autowired
  protected MockMvc mockMvc;

  @Autowired
  protected ObjectMapper objectMapper; // 직렬화, 역직렬화를 위한 클래스

  @Autowired
  protected WebApplicationContext context;

  @Autowired
  BlogRepository blogRepository;

  @Autowired
  UserRepository userRepository;

  User user;

  @BeforeEach
  public void setMockMvc() {
    mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    blogRepository.deleteAll();
  }

  @BeforeEach
  void setSecurityContext() {
    userRepository.deleteAll();
    user = userRepository.save(User.builder()
            .email("suwan@gmail.com")
            .password("suwan")
            .build());

    SecurityContext context = SecurityContextHolder.getContext();
    context.setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
  }

  @DisplayName("addArticle: 블로그에 글 추가를 성공한다.")
  @Test
  void addArticle() throws Exception {
    // given
    final String url = "/api/articles";
    final String title = "title";
    final String content = "content";
    final ArticleRequest articleRequest = new ArticleRequest(title, content);

    Principal principal = Mockito.mock(Principal.class);
    Mockito.when(principal.getName()).thenReturn("username");
    // 객체 -> JSON 직렬화
    final String requestBody = objectMapper.writeValueAsString(articleRequest);

    // when 설정한 내용을 바탕으로 요청 전송
    ResultActions result = mockMvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .principal(principal)
            .content(requestBody));

    // then
    result.andExpect(status().isCreated());
    List<Article> articles = blogRepository.findAll();

    assertThat(articles.size()).isEqualTo(1);
    assertThat(articles.get(0).getTitle()).isEqualTo(title);
    assertThat(articles.get(0).getContent()).isEqualTo(content);
  }

  @DisplayName("findAllArticles: 블로그 글 목록 조회에 성공한다.")
  @Test
  void findAllArticles() throws Exception {
    //given
    final String url = "/api/articles";
    Article defaultArticle = createDefaultArticle();

    // when
    ResultActions result = mockMvc.perform(get(url)
            .accept(MediaType.APPLICATION_JSON));

    // then
    result.andExpect(status().isOk())
            .andExpect(jsonPath("[0].content").value(defaultArticle.getContent()))
            .andExpect(jsonPath("[0].title").value(defaultArticle.getTitle()));
  }


  @DisplayName("findArticle: 블로그 글 조회(단건 조회)에 성공한다.")
  @Test
  void findArticle() throws Exception {
    // given
    final String url = "/api/articles/{id}";
    Article defaultArticle = createDefaultArticle();

    // when
    ResultActions result = mockMvc.perform(get(url, defaultArticle.getId()));

    // then
    result.andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value(defaultArticle.getTitle()))
            .andExpect(jsonPath("$.content").value(defaultArticle.getContent()));
  }

  @DisplayName("deleteArticle: 블로그 글 삭제에 성공한다.")
  @Test
  void deleteArticle() throws Exception {
    // given
    final String url = "/api/articles/{id}";
    Article defaultArticle = createDefaultArticle();

    // when
    mockMvc.perform(delete(url, defaultArticle.getId()))
            .andExpect(status().isOk()); // then에서 안하네? ..

    // then
    List<Article> articles = blogRepository.findAll();
    assertThat(articles).isEmpty();
  }

  @DisplayName("updateArticle: 블로그 글 수정에 성공한다.")
  @Test
  void updateArticle() throws Exception {
    // given
    final String url = "/api/articles/{id}";
    Article defaultArticle = createDefaultArticle();

    final String newTitle = "new Title!! ";
    final String newContent = "new Content!! ";

    UpdateArticleRequest updateArticleRequest = new UpdateArticleRequest(newTitle, newContent);

    // when
    ResultActions result = mockMvc.perform(put(url, defaultArticle.getId())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(updateArticleRequest)));

    // then
    result.andExpect(status().isOk());

    Article article = blogRepository.findById(defaultArticle.getId()).get();

    assertThat(article.getTitle()).isEqualTo(newTitle);
    assertThat(article.getContent()).isEqualTo(newContent);
  }

  private Article createDefaultArticle() {
    return blogRepository.save(Article.builder()
            .title("title")
            .author(user.getUsername())
            .content("content")
            .build());
  }

}