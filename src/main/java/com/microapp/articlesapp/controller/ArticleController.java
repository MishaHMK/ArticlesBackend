package com.microapp.articlesapp.controller;

import com.microapp.articlesapp.dto.articles.ArticleDto;
import com.microapp.articlesapp.dto.articles.CreateArticleDto;
import com.microapp.articlesapp.dto.articles.UpdateArticleDto;
import com.microapp.articlesapp.service.article.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Article Management", description = "Endpoints for managing articles")
@RequiredArgsConstructor
@RestController
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping
    @Operation(summary = "Get all articles",
            description = "Get all articles with pagination/sorting")
    public Page<ArticleDto> getAll(@ParameterObject Pageable pageable) {
        return articleService.getAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get article by id",
            description = "Get specific article data by its id")
    public ArticleDto getById(@PathVariable Long id) {
        return articleService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create article",
            description = "Create new article with given data")
    public ArticleDto createNew(@Valid @RequestBody CreateArticleDto createArticleDto) {
        return articleService.save(createArticleDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete article by id",
            description = "Mark specific article by given id as removed")
    public void deleteById(@PathVariable Long id) {
        articleService.deleteById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update article by id",
            description = "Update specific article with given data by its id")
    public ArticleDto updateById(@PathVariable Long id,
                                @Valid @RequestBody UpdateArticleDto updateDto) {
        return articleService.update(id, updateDto);
    }
}
