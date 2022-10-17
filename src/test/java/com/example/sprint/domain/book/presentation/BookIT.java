package com.example.sprint.domain.book.presentation;

import com.example.sprint.domain.author.dto.AuthorInfoDto;
import com.example.sprint.domain.book.application.BookService;
import com.example.sprint.domain.book.domain.Book;
import com.example.sprint.domain.book.domain.Currency;
import com.example.sprint.domain.book.dto.BookDto;
import com.example.sprint.domain.book.dto.CreateBookReq;
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
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookIT {

    @Autowired
    MockMvc mvc;

    @MockBean
    BookService bookService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("책 생성(등록) 통합테스트")
    void createBookIT() throws Exception {
        // given
        Long TEST_BOOK_ID = 1L;
        CreateBookReq req
                = new CreateBookReq(List.of(1L,2L),
                "bookName",
                100,
                "950926",
                "978-0-306-40615-7",
                "10000",
                Currency.KRW);
        Book book = Book.createBook(req.getName(), req.getTotalPages(), req.getPublicationOfYear(),
                req.getIsbn(), req.getPrice(), req.getCurrency(),req.getAuthorIds());
        Field field = Book.class.getDeclaredField("id");
        field.setAccessible(true);
        field.set(book, 1L);
        when(bookService.createBook(req)).thenReturn(TEST_BOOK_ID);
        String body = objectMapper.writeValueAsString(req);
        // when
        ResultActions resultActions = mvc.perform(
                        post("/api/book")
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
    @DisplayName("전체 책 조회 통합테스트")
    void getAllBookListIT() throws Exception {
        // given
        int startPage = 0;
        int bookCounts = 10;
        Book book = Book.createBook(
                "bookName",
                100,
                "950926",
                "978-0-306-40615-7",
                "10000",
                Currency.KRW,
                List.of(1L,2L));
        AuthorInfoDto authorInfoDto = new AuthorInfoDto("testName", "950926");
        BookDto bookDto = BookDto.from(book, List.of(authorInfoDto));
        when(bookService.getAllBookList(startPage,bookCounts)).thenReturn(List.of(bookDto));
        // when
        ResultActions resultActions = mvc.perform(
                        get("/api/book")
                                .param("startPages", "0")
                                .param("bookCounts", "10")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("data[0].name",is(book.getName())))
                .andExpect(jsonPath("data[0].authors[0].name",is(authorInfoDto.getName())));
    }

    @Test
    @DisplayName("책 생성(등록)_잘못된 isbn 통합테스트")
    void createBookIT_WrongIsbn() throws Exception {
        // given
        CreateBookReq req
                = new CreateBookReq(List.of(1L,2L),
                "bookName",
                100,
                "950926",
                "worngIsbn",
                "10000",
                Currency.KRW);
        String body = objectMapper.writeValueAsString(req);
        // when
        ResultActions resultActions = mvc.perform(
                        post("/api/book")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
        // then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors[0].reason", is("isbn 패턴을 맞춰서 입력해주세요")));
    }

    @Test
    @DisplayName("책 생성(등록)_가격 입력시 소수점 두자리 이상 입력시 통합테스트")
    void createBookIT_WrongPrice() throws Exception {
        // given
        CreateBookReq req
                = new CreateBookReq(List.of(1L,2L),
                "bookName",
                100,
                "950926",
                "978-0-306-40615-7",
                "10000.000",
                Currency.KRW);
        String body = objectMapper.writeValueAsString(req);
        // when
        ResultActions resultActions = mvc.perform(
                        post("/api/book")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());
        // then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors[0].reason", is("가격은 소수점 2자리수까지만 입력 가능합니다.")));
    }







}