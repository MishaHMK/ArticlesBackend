package com.microapp.articlesapp.service;

import com.microapp.articlesapp.dto.articles.ArticleDto;
import com.microapp.articlesapp.dto.articles.CreateArticleDto;
import com.microapp.articlesapp.dto.articles.UpdateArticleDto;
import com.microapp.articlesapp.exception.AccessException;
import com.microapp.articlesapp.mapper.ArticleMapper;
import com.microapp.articlesapp.model.Article;
import com.microapp.articlesapp.model.User;
import com.microapp.articlesapp.repository.article.ArticleRepository;
import com.microapp.articlesapp.security.SecurityUtil;
import com.microapp.articlesapp.service.article.ArticleServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class ArticleServiceImplTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private ArticleMapper articleMapper;

    @InjectMocks
    private ArticleServiceImpl articleService;

    @Test
    void getById_articleExists_success() {
        Article article = new Article().setId(1L);

        ArticleDto articleDto = new ArticleDto();

        Mockito.when(articleRepository.findById(1L)).thenReturn(Optional.of(article));
        Mockito.when(articleMapper.toDto(article)).thenReturn(articleDto);

        ArticleDto result = articleService.getById(1L);

        Assertions.assertEquals(articleDto, result);
    }

    @Test
    void getById_articleNotFound_throwsException() {
        Mockito.when(articleRepository.findById(99L)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> articleService.getById(99L));
    }

    @Test
    void getAll_success() {
        Article article = new Article();
        ArticleDto dto = new ArticleDto();
        Page<Article> articlePage = new PageImpl<>(List.of(article));

        Mockito.when(articleRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(articlePage);
        Mockito.when(articleMapper.toDto(article)).thenReturn(dto);

        Page<ArticleDto> result = articleService.getAll(
                PageRequest.of(0, 10));

        Assertions.assertEquals(1, result.getContent().size());
        Assertions.assertEquals(dto, result.getContent().get(0));
    }

    @Test
    void save_success() {
        CreateArticleDto createDto = new CreateArticleDto("Test Title",
                "Test content text");
        Article article = new Article();
        Article savedArticle = new Article();
        ArticleDto dto = new ArticleDto();
        User user = new User().setId(10L);

        Mockito.when(articleMapper.toEntity(createDto)).thenReturn(article);
        Mockito.when(articleRepository.save(article)).thenReturn(savedArticle);
        Mockito.when(articleMapper.toDto(savedArticle)).thenReturn(dto);

        try (MockedStatic<SecurityUtil> securityUtilMock = Mockito
                .mockStatic(SecurityUtil.class)) {
            securityUtilMock.when(SecurityUtil::getLoggedInUser).thenReturn(user);

            ArticleDto result = articleService.save(createDto);

            Assertions.assertEquals(dto, result);
            Assertions.assertEquals(user, article.getAuthor());
            Assertions.assertNotNull(article.getPublicationDate());
            Mockito.verify(articleRepository).save(article);
        }
    }

    @Test
    void update_successByOwner() {
        UpdateArticleDto updateDto = new UpdateArticleDto("Test Title",
                "Test content text");
        User author = new User().setId(5L).setRole(User.Role.AUTHOR);
        Article article = new Article().setAuthor(author);
        ArticleDto dto = new ArticleDto();

        Mockito.when(articleRepository.findById(1L)).thenReturn(Optional.of(article));
        Mockito.when(articleMapper.toDto(article)).thenReturn(dto);

        try (MockedStatic<SecurityUtil> securityUtilMock =
                     Mockito.mockStatic(SecurityUtil.class)) {
            securityUtilMock.when(SecurityUtil::getLoggedInUser).thenReturn(author);

            ArticleDto result = articleService.update(1L, updateDto);

            Assertions.assertEquals(dto, result);
            Mockito.verify(articleMapper).updateFromDto(updateDto, article);
        }
    }

    @Test
    void update_notOwnerAndNotAdmin_throwsAccessException() {
        UpdateArticleDto updateDto = new UpdateArticleDto("Test Title",
                "Test content text");
        User author = new User().setId(1L);
        Article article = new Article().setAuthor(author);

        User otherUser = new User()
                .setId(2L)
                .setRole(User.Role.AUTHOR);

        Mockito.when(articleRepository.findById(1L)).thenReturn(Optional.of(article));

        try (MockedStatic<SecurityUtil> securityUtilMock =
                     Mockito.mockStatic(SecurityUtil.class)) {
            securityUtilMock.when(SecurityUtil::getLoggedInUser).thenReturn(otherUser);

            Assertions.assertThrows(AccessException.class,
                    () -> articleService.update(1L, updateDto));
        }
    }

    @Test
    void deleteById_successAsAdmin() {
        User author = new User().setId(1L);
        Article article = new Article().setAuthor(author);

        User admin = new User()
                .setId(2L)
                .setRole(User.Role.ADMIN);

        Mockito.when(articleRepository.findById(1L)).thenReturn(Optional.of(article));

        try (MockedStatic<SecurityUtil> securityUtilMock =
                     Mockito.mockStatic(SecurityUtil.class)) {
            securityUtilMock.when(SecurityUtil::getLoggedInUser).thenReturn(admin);

            articleService.deleteById(1L);

            Mockito.verify(articleRepository).deleteById(1L);
        }
    }
}
