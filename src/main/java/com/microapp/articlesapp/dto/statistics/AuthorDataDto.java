package com.microapp.articlesapp.dto.statistics;

public record AuthorDataDto(Long authorId,
                            String authorFirstName,
                            String authorLastName,
                            Long articlesCount) {
}
