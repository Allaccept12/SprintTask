package com.example.sprint.domain.book.application;

import com.example.sprint.domain.book.dto.BookDto;
import com.example.sprint.domain.book.dto.CreateBookReq;

import java.util.List;

public interface BookService {

    Long createBook(CreateBookReq req);
    List<BookDto> getAllBookList(int startPages, int bookCounts);
}
