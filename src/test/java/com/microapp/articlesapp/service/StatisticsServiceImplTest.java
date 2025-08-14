package com.microapp.articlesapp.service;

import com.microapp.articlesapp.dto.statistics.AuthorDataDto;
import com.microapp.articlesapp.repository.article.ArticleRepository;
import com.microapp.articlesapp.service.statistics.StatisticsServiceImpl;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class StatisticsServiceImplTest {

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private StatisticsServiceImpl statisticsService;

    @Test
    void receiveTopAuthors_success() {
        int topSize = 3;
        int daysLimit = 50;

        AuthorDataDto firstAuthor = new AuthorDataDto(
                1L, "Peter", "Doe",10L);
        AuthorDataDto secondAuthor = new AuthorDataDto(
                2L, "Alice", "Jade", 8L);

        List<AuthorDataDto> expected = List.of(firstAuthor, secondAuthor);
        Page<AuthorDataDto> page = new PageImpl<>(expected);

        OffsetDateTime fromDate = LocalDate.now()
                .minusDays(daysLimit)
                .atStartOfDay()
                .atOffset(ZoneOffset.UTC);

        PageRequest pageRequest = PageRequest.of(0, topSize);

        Mockito.when(articleRepository.findTopAuthors(pageRequest, fromDate))
                .thenReturn(page);

        List<AuthorDataDto> result = statisticsService
                .receiveTopAuthors(topSize, daysLimit);

        Assertions.assertEquals(expected, result);
        Mockito.verify(articleRepository).findTopAuthors(pageRequest, fromDate);
    }
}
