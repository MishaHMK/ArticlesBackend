package com.microapp.articlesapp.service.article;

import com.microapp.articlesapp.dto.articles.ArticleDto;
import com.microapp.articlesapp.dto.articles.CreateArticleDto;
import com.microapp.articlesapp.dto.articles.UpdateArticleDto;
import com.microapp.articlesapp.exception.AccessException;
import com.microapp.articlesapp.mapper.ArticleMapper;
import com.microapp.articlesapp.model.Article;
import com.microapp.articlesapp.model.User;
import com.microapp.articlesapp.repository.article.ArticleRepository;
import com.microapp.articlesapp.security.SecurityUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    @Override
    public ArticleDto getById(Long id) {
        Article articleById = getArticleById(id);
        return articleMapper.toDto(articleById
        );
    }

    @Override
    public Page<ArticleDto> getAll(Pageable pageable) {
        return articleRepository.findAll(pageable)
                .map(articleMapper::toDto);
    }

    @Transactional
    @Override
    public ArticleDto save(CreateArticleDto createDto) {
        Article toCreate = articleMapper.toEntity(createDto);
        toCreate.setPublicationDate(OffsetDateTime.now());
        toCreate.setAuthor(SecurityUtil.getLoggedInUser());
        return articleMapper.toDto(articleRepository.save(toCreate));
    }

    @Transactional
    @Override
    public ArticleDto update(Long id, UpdateArticleDto updateDto) {
        Article articleToUpdate = getArticleById(id);
        checkOwnership(articleToUpdate);
        articleMapper.updateFromDto(updateDto, articleToUpdate);
        return articleMapper.toDto(articleToUpdate);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        Article articleToRemove = getArticleById(id);
        checkOwnership(articleToRemove);
        articleRepository.deleteById(id);
    }

    private Article getArticleById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Article with id "
                                + id + " not found"));
    }

    private void checkOwnership(Article articleToCheck) {
        User currentUser = SecurityUtil.getLoggedInUser();
        boolean isAdmin = currentUser.getRole().equals(User.Role.ADMIN);
        boolean isOwner = currentUser.getId().equals(articleToCheck.getAuthor().getId());

        if (!isAdmin && !isOwner) {
            throw new AccessException("You are not allowed to modify this article");
        }
    }
}
