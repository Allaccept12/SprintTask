package com.example.sprint.domain.book.application;

import com.example.sprint.domain.author.domain.Author;
import com.example.sprint.domain.author.repository.AuthorRepository;
import com.example.sprint.domain.author.dto.AuthorInfoDto;
import com.example.sprint.domain.book.domain.Book;
import com.example.sprint.domain.book.repository.BookRepository;
import com.example.sprint.domain.book.dto.BookDto;
import com.example.sprint.domain.book.dto.CreateBookReq;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Transactional
    public Long createBook(CreateBookReq req) {
        Book book = Book.createBook(req.getName(), req.getTotalPages(), req.getPublicationOfYear(),
                req.getIsbn(), req.getPrice(), req.getCurrency(),req.getAuthorIds());
        return bookRepository.save(book).getId();
    }

    @Transactional(readOnly = true)
    public List<BookDto> getAllBookList(int startPages, int bookCounts) {
        Pageable page = PageRequest.of(startPages,bookCounts);
        Page<Book> bookList = bookRepository.findAll(page);
        List<BookDto> bookDtoList = new ArrayList<>();
        for (Book book : bookList) {
            List<Author> authors = authorRepository.findByIdIn(book.getAuthorIds());
            List<AuthorInfoDto> authorInfoDtoList = getAuthorInfoDtoList(authors);
            bookDtoList.add(BookDto.from(book,authorInfoDtoList));
        }
        return bookDtoList;
    }

    private List<AuthorInfoDto> getAuthorInfoDtoList(List<Author> authors) {
        return authors.stream()
                .map(data -> new AuthorInfoDto(data.getName(), data.getBirth()))
                .collect(Collectors.toList());
    }
}
