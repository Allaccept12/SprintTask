package com.example.sprint.domain.author.appication;


import com.example.sprint.domain.author.domain.Author;
import com.example.sprint.domain.author.repository.AuthorRepository;
import com.example.sprint.domain.author.dto.AuthorInfoDto;
import com.example.sprint.domain.author.dto.CreateAuthorReq;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Transactional
    public Long createAuthor(CreateAuthorReq req) {
        Author author = Author.createAuthor(req.getName(), req.getBirth());
        return authorRepository.save(author).getId();
    }

    @Transactional(readOnly = true)
    public AuthorInfoDto getAuthorInfo(Long id) {
        Author author = getAuthor(id);
        return new AuthorInfoDto(author.getName(),author.getBirth());
    }

    public Author getAuthor(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(AuthorNotFoundException::new);
    }
}
