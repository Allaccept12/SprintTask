package com.example.sprint.domain.book.presentation;


import com.example.sprint.domain.book.application.BookService;
import com.example.sprint.domain.book.dto.BookDto;
import com.example.sprint.domain.book.dto.CreateBookReq;
import com.example.sprint.global.common.model.ResponseDto;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/api/book")
    @ApiOperation(value = "책 생성 api")
    public ResponseDto<?> createBook(@Valid @RequestBody CreateBookReq req) {
        Long bookId = bookService.createBook(req);
        return ResponseDto.success(bookId);
    }

    @GetMapping("/api/book")
    @ApiOperation(value = "책 전체 조회 api")
    public ResponseDto<?> getAllBookList(@RequestParam(value = "startPages") int startPages,
                                         @RequestParam(value = "bookCounts") int bookCounts) {
        List<BookDto> allBookList = bookService.getAllBookList(startPages, bookCounts);
        return ResponseDto.success(allBookList);
    }
}
