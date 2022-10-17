package com.example.sprint.domain.author.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id",nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String birth;

    private Author(String name, String birth) {
        this.name = name;
        this.birth = birth;
    }

    public static Author createAuthor(String name,String birth) {
        return new Author(name,birth);
    }
}
