package com.microapp.articlesapp.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microapp.articlesapp.dto.articles.ArticleDto;
import com.microapp.articlesapp.dto.articles.CreateArticleDto;
import com.microapp.articlesapp.dto.articles.UpdateArticleDto;
import com.microapp.articlesapp.security.jwt.JwtAuthFilter;
import com.microapp.articlesapp.security.jwt.JwtUtil;
import com.microapp.articlesapp.service.article.ArticleService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(ArticleController.class)
@AutoConfigureMockMvc(addFilters = false)
class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;

    @MockBean
    private JwtAuthFilter jwtAuthFilter;

    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAll_returnsPageOfArticles() throws Exception {
        ArticleDto dto = new ArticleDto();
        Page<ArticleDto> page = new PageImpl<>(List.of(dto));

        Mockito.when(articleService.getAll(Mockito.any(Pageable.class)))
                .thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/articles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());

        Mockito.verify(articleService).getAll(Mockito.any(Pageable.class));
    }

    @Test
    void getById_returnsArticle() throws Exception {
        ArticleDto dto = new ArticleDto();
        dto.setId(1L);

        Mockito.when(articleService.getById(1L)).thenReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders.get("/articles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        Mockito.verify(articleService).getById(1L);
    }

    @Test
    void createNew_returnsCreatedArticle() throws Exception {
        CreateArticleDto createDto = new CreateArticleDto("Title",
                "Content text");
        ArticleDto dto = new ArticleDto();
        dto.setId(100L);

        Mockito.when(articleService.save(Mockito.any(CreateArticleDto.class)))
                .thenReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders.post("/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(100L));

        Mockito.verify(articleService).save(Mockito.any(CreateArticleDto.class));
    }

    @Test
    void deleteById_returnsNoContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/articles/5"))
                .andExpect(status().isNoContent());

        Mockito.verify(articleService).deleteById(5L);
    }

    @Test
    void updateById_returnsUpdatedArticle() throws Exception {
        UpdateArticleDto updateDto = new UpdateArticleDto("Updated title",
                "Updated content");
        ArticleDto dto = new ArticleDto();
        dto.setId(7L);

        Mockito.when(articleService.update(Mockito.eq(7L),
                        Mockito.any(UpdateArticleDto.class)))
                .thenReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders.put("/articles/7")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7L));

        Mockito.verify(articleService).update(Mockito.eq(7L),
                Mockito.any(UpdateArticleDto.class));
    }
}
