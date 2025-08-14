package com.microapp.articlesapp.service.statistics;

import com.microapp.articlesapp.dto.statistics.AuthorDataDto;
import java.util.List;

public interface StatisticsService {
    List<AuthorDataDto> receiveTopAuthors(int topSize, int daysLimit);
}
