package com.example.sprint.domain.book.application;

import com.example.sprint.domain.author.appication.AuthorNotFoundException;
import com.example.sprint.domain.author.domain.Author;
import com.example.sprint.domain.author.repository.AuthorRepository;
import com.example.sprint.domain.book.domain.Book;
import com.example.sprint.domain.book.domain.Currency;
import com.example.sprint.domain.book.dto.BookDto;
import com.example.sprint.domain.book.dto.CreateBookReq;
import com.example.sprint.domain.book.repository.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Field;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;


@MockitoSettings(strictness = Strictness.WARN)
class BookServiceTest {

    @InjectMocks
    BookServiceImpl bookService;

    @Mock
    BookRepository bookRepository;

    @Mock
    AuthorRepository authorRepository;

    @Test
    @DisplayName("책 생성(등록)")
    void createBook() throws NoSuchFieldException, IllegalAccessException {
        // given
        Long TEST_ID = 1L;
        CreateBookReq req
                = new CreateBookReq(List.of(1L,2L),
                "bookName",
                100,
                "950926",
                "testISBN",
                "10000",
                Currency.KRW);
        Book book = Book.createBook(req.getName(), req.getTotalPages(), req.getPublicationOfYear(),
                req.getIsbn(), req.getPrice(), req.getCurrency(),req.getAuthorIds());
        Field field = Book.class.getDeclaredField("id");
        field.setAccessible(true);
        field.set(book,TEST_ID);

        given(bookRepository.save(any(Book.class))).willReturn(book);
        // when
        Long bookId = bookService.createBook(req);
        // then
        assertThat(bookId).isEqualTo(TEST_ID);
    }

    @Test
    @DisplayName("책 생성(등록)_가격 입력시 Currency를 입력 안했을때 ")
    void createBook_InputPrice_NotInputCurrency() throws NoSuchFieldException, IllegalAccessException {
        // given
        CreateBookReq req
                = new CreateBookReq(List.of(1L,2L),
                "bookName",
                100,
                "950926",
                "testISBN",
                "10000",
                null);

        // when - then
        assertThatThrownBy(() -> {
            bookService.createBook(req);
        }).isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("전체 책 조회")
    void getAllBookList() {
        // given
        int startPages = 0;
        int bookCounts = 10;
        Pageable page = PageRequest.of(startPages,bookCounts);
        List<Long> authorIds = List.of(1L, 2L, 3L);
        Author author = Author.createAuthor("testName", "950926");
        Book book = Book.createBook("testName", 100, "20100110",
                "testISBN", "11000", Currency.KRW,authorIds);

        Page<Book> bookList = new PageImpl<>(List.of(book,book));
        given(bookRepository.findAll(page)).willReturn(bookList);
        given(authorRepository.findByIdIn(authorIds)).willReturn(List.of(author));
        // when
        List<BookDto> result = bookService.getAllBookList(startPages, bookCounts);
        // then
        assertThat(result.get(0).getName()).isEqualTo(book.getName());
    }

}