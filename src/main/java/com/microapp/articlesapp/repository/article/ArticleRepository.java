package com.microapp.articlesapp.repository.article;

import com.microapp.articlesapp.dto.statistics.AuthorDataDto;
import com.microapp.articlesapp.model.Article;
import java.time.OffsetDateTime;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Override
    @NonNull
    @EntityGraph(attributePaths = {"author"})
    Page<Article> findAll(Pageable pageable);

    @Query("SELECT new com.microapp.articlesapp.dto.statistics"
            + ".AuthorDataDto(author.id, author.firstName, author.lastName, COUNT(article))"
            + " FROM Article article "
            + "JOIN article.author author  "
            + "WHERE author.isDeleted = false "
            + "AND article.publicationDate >= :fromDate "
            + "GROUP BY author.id, author.firstName, author.firstName "
            + "ORDER BY COUNT(article) DESC")
    Page<AuthorDataDto> findTopAuthors(Pageable pageable, OffsetDateTime fromDate);
}
