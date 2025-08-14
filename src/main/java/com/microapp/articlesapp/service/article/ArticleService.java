package com.microapp.articlesapp.service.article;

import com.microapp.articlesapp.dto.articles.ArticleDto;
import com.microapp.articlesapp.dto.articles.CreateArticleDto;
import com.microapp.articlesapp.dto.articles.UpdateArticleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleService {
    ArticleDto getById(Long id);

    Page<ArticleDto> getAll(Pageable pageable);

    ArticleDto save(CreateArticleDto createDto);

    ArticleDto update(Long id, UpdateArticleDto updateDto);

    void deleteById(Long id);
}
