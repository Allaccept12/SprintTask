package com.example.sprint.domain.author.presentation;


import com.example.sprint.domain.author.appication.AuthorService;
import com.example.sprint.domain.author.appication.AuthorServiceImpl;
import com.example.sprint.domain.author.dto.AuthorInfoDto;
import com.example.sprint.domain.author.dto.CreateAuthorReq;
import com.example.sprint.global.common.model.ResponseDto;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping("/api/author")
    @ApiOperation(value = "저자 생성 api")
    public ResponseDto<?> createAuthor(@Valid @RequestBody CreateAuthorReq req) {
        Long authorId = authorService.createAuthor(req);
        return ResponseDto.success(authorId);
    }

    @GetMapping("/api/author/{authorId}")
    @ApiOperation(value = "저자 조회 api")
    public ResponseDto<?> getAuthorInfo(@PathVariable Long authorId) {
        AuthorInfoDto authorInfoDto = authorService.getAuthorInfo(authorId);
        return ResponseDto.success(authorInfoDto);
    }
}
