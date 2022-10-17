package com.example.sprint.domain.book.dto;


import com.example.sprint.domain.author.dto.AuthorInfoDto;
import com.example.sprint.domain.book.domain.Book;
import com.example.sprint.domain.book.domain.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class BookDto {

    private Long id;

    private String name;

    private boolean extinction;

    private int totalPages;

    private String publicationOfYear;

    private String isbn;

    private String price;

    private Currency currency;

    private List<AuthorInfoDto> authors;

    public static BookDto from(Book book, List<AuthorInfoDto> authorInfoDtoList) {
        return BookDto.builder()
                .id(book.getId())
                .name(book.getName())
                .extinction(book.isExtinction())
                .totalPages(book.getTotalPages())
                .publicationOfYear(book.getPublicationOfYear())
                .isbn(book.getIsbn())
                .price(book.getPrice())
                .currency(book.getCurrency())
                .authors(authorInfoDtoList)
                .build();
    }

}
