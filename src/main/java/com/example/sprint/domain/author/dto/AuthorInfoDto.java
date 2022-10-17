package com.example.sprint.domain.author.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthorInfoDto {

    private String name;
    private String birth;

    public AuthorInfoDto() {
    }
}
