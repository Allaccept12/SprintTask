package com.example.sprint.domain.author.repository;

import com.example.sprint.domain.author.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findByIdIn(List<Long> ids);
}
