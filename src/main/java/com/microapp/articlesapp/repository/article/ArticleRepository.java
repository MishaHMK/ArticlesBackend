package com.microapp.articlesapp.repository.article;

import com.microapp.articlesapp.model.Article;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Override
    @NonNull
    @EntityGraph(attributePaths = {"author"})
    Page<Article> findAll(Pageable pageable);
}
