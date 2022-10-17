package com.example.sprint.domain.book.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String name;

    @Column(nullable = false)
    private boolean extinction;

    @Column(nullable = false)
    private Integer totalPages;

    @Column(nullable = false)
    private String publicationOfYear;

    @Column(nullable = false)
    private String isbn;

    private String price;

    private Currency currency;

    @ElementCollection
    @CollectionTable(name = "BookAuthors",
            joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "author_id")
    private List<Long> authorIds;

    private Book(String name,Integer totalPages, String publicationOfYear,String isbn,
                 String price, Currency currency, List<Long> authorIds) {
        this.name = name;
        this.extinction = false;
        this.totalPages = totalPages;
        this.publicationOfYear = publicationOfYear;
        this.isbn = isbn;
        this.authorIds = authorIds;
        this.price = price;
        this.currency = price != null ? setCurrency(currency) : null;
    }

    private Currency setCurrency(Currency currency) {
        if (currency == null) {
            throw new IllegalStateException("가격을 입력하시려면 통화(Currency)를 입력해주세요.");
        }
        return currency;
    }

    public static Book createBook(String name,int totalPages, String publicationOfYear,
                                  String isbn, String price, Currency currency, List<Long> authorIds) {
        return new Book(name,totalPages,publicationOfYear,isbn,price,currency,authorIds);
    }


}
