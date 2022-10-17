package com.example.sprint.domain.author.presentation;

import com.example.sprint.domain.author.appication.AuthorNotFoundException;
import com.example.sprint.domain.author.appication.AuthorService;
import com.example.sprint.domain.author.domain.Author;
import com.example.sprint.domain.author.dto.AuthorInfoDto;
import com.example.sprint.domain.author.dto.CreateAuthorReq;
import com.example.sprint.domain.author.repository.AuthorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.lang.reflect.Field;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthorIT{


    @Autowired
    MockMvc mvc;

    @MockBean
    AuthorService authorService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("저자 생성 통합테스트")
    void createAuthorIT() throws Exception {
        // given
        Long TEST_AUTHOR_ID = 1L;
        CreateAuthorReq req = new CreateAuthorReq("name","950926");
        Author author = Author.createAuthor(req.getName(), req.getBirth());
        Field field = Author.class.getDeclaredField("id");
        field.setAccessible(true);
        field.set(author, 1L);
        when(authorService.createAuthor(req)).thenReturn(TEST_AUTHOR_ID);
        String body = objectMapper.writeValueAsString(req);
        // when
        ResultActions resultActions = mvc.perform(
                        post("/api/author")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data",is(notNullValue())));

    }

    @Test
    @DisplayName("저자 조회 통합테스트")
    void getAuthorInfoIT() throws Exception {
        // given
        AuthorInfoDto authorInfoDto = new AuthorInfoDto("name", "950926");
        when(authorService.getAuthorInfo(anyLong())).thenReturn(authorInfoDto);
        // when
        ResultActions resultActions = mvc.perform(
                        get("/api/author/{authorId}", anyLong())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.name",is(authorInfoDto.getName())))
                .andExpect(jsonPath("data.birth",is(authorInfoDto.getBirth())));
    }
    @Test
    @DisplayName("저자 조회 - 저자 찾을 수 없음 통합테스트")
    void getAuthorInfoIT_NotFound() throws Exception {
        // given

        when(authorService.getAuthorInfo(anyLong())).thenThrow(AuthorNotFoundException.class);
        // when
        ResultActions resultActions = mvc.perform(
                        get("/api/author/{authorId}", anyLong())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
        // then
        resultActions
                .andExpect(status().isNotFound());
    }
}