package com.microapp.articlesapp.controller;

import com.microapp.articlesapp.dto.statistics.AuthorDataDto;
import com.microapp.articlesapp.service.statistics.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Statistics Management", description = "Endpoints for managing statistics")
@RequiredArgsConstructor
@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    private final StatisticsService statisticsService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/top-3")
    @Operation(summary = "Get top three authors for last 50 days",
            description = "Get top three authors by articles for last 50 days."
                    + " For ADMIN Role users only")
    public List<AuthorDataDto> getAll() {
        return statisticsService.receiveTopAuthors(3, 50);
    }
}
