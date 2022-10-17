package com.example.sprint.domain.author.appication;

import com.example.sprint.domain.author.dto.AuthorInfoDto;
import com.example.sprint.domain.author.dto.CreateAuthorReq;

public interface AuthorService {

    Long createAuthor(CreateAuthorReq req);
    AuthorInfoDto getAuthorInfo(Long id);
}
