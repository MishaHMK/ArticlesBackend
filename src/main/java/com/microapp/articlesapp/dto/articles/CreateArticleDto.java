package com.microapp.articlesapp.dto.articles;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CreateArticleDto(
        @NotBlank(message = "Title for article is required")
        @Length(max = 100, message = "Title can't be longer than 100 symbols")
        String title,
        @NotBlank(message = "Content of article is required")
        @Length(max = 10000, message = "Your content text can't exceed 10000 symbols")
        String content) {
}
