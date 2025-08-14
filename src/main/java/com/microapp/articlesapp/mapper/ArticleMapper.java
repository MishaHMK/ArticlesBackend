package com.microapp.articlesapp.mapper;

import com.microapp.articlesapp.config.MapperConfig;
import com.microapp.articlesapp.dto.articles.ArticleDto;
import com.microapp.articlesapp.dto.articles.CreateArticleDto;
import com.microapp.articlesapp.dto.articles.UpdateArticleDto;
import com.microapp.articlesapp.model.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class,
        uses = {UserMapper.class})
public interface ArticleMapper {
    Article toEntity(CreateArticleDto dto);

    @Mapping(source = "author.id", target = "authorId")
    ArticleDto toDto(Article article);

    void updateFromDto(UpdateArticleDto dto,
                       @MappingTarget Article article);
}
