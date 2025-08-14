package com.microapp.articlesapp.dto.articles;

import java.time.OffsetDateTime;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ArticleDto {
    private Long id;
    private OffsetDateTime publicationDate;
    private Long authorId;
    private String title;
    private String content;
}
