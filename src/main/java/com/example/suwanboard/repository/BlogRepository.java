package com.example.suwanboard.repository;

import com.example.suwanboard.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article, Long> {
}
