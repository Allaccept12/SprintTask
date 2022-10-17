package com.example.sprint.domain.author.appication;

import com.example.sprint.domain.author.domain.Author;
import com.example.sprint.domain.author.dto.AuthorInfoDto;
import com.example.sprint.domain.author.dto.CreateAuthorReq;
import com.example.sprint.domain.author.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;


@MockitoSettings(strictness = Strictness.WARN)
class AuthorServiceTest {

    @InjectMocks
    AuthorServiceImpl authorService;

    @Mock
    AuthorRepository authorRepository;

    @Test
    @DisplayName("저자 생성")
    void createAuthor() throws NoSuchFieldException, IllegalAccessException {
        // given
        Long TEST_ID = 1L;
        CreateAuthorReq req = new CreateAuthorReq("name","950926");
        Author author = Author.createAuthor(req.getName(), req.getBirth());
        Field field = Author.class.getDeclaredField("id");
        field.setAccessible(true);
        field.set(author, TEST_ID);
        given(authorRepository.save(any(Author.class))).willReturn(author);
        // when
        Long authorId = authorService.createAuthor(req);
        // then
        assertThat(authorId).isEqualTo(TEST_ID);
    }

    @Test
    @DisplayName("저자 조회")
    void getAuthorInfo() {
        // given
        Author author = Author.createAuthor("name","950926");
        given(authorRepository.findById(anyLong())).willReturn(Optional.of(author));
        // when
        AuthorInfoDto authorInfo = authorService.getAuthorInfo(anyLong());
        // then
        assertThat(authorInfo.getName()).isEqualTo(author.getName());
    }

    @Test
    @DisplayName("저자 조회 실패")
    void getAuthorInfo_NotFound() {
        // given
        given(authorRepository.findById(anyLong())).willThrow(AuthorNotFoundException.class);
        // when - then
        assertThatThrownBy(() -> {
            authorService.getAuthorInfo(anyLong());
        }).isInstanceOf(AuthorNotFoundException.class);
    }

}