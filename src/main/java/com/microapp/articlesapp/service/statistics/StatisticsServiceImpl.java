package com.microapp.articlesapp.service.statistics;

import com.microapp.articlesapp.dto.statistics.AuthorDataDto;
import com.microapp.articlesapp.repository.article.ArticleRepository;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {
    private final ArticleRepository articleRepository;

    @Override
    public List<AuthorDataDto> receiveTopAuthors(int topSize, int daysLimit) {
        PageRequest top = PageRequest.of(0, topSize);
        OffsetDateTime fromDate = LocalDate.now().minusDays(daysLimit).atStartOfDay().atOffset(ZoneOffset.UTC);
        return articleRepository.findTopAuthors(top, fromDate).getContent();
    }
}
