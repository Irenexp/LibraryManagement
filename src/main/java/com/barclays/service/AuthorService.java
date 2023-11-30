package com.barclays.service;

import com.barclays.model.Author;
import com.barclays.model.Book;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    Author saveAuthor(Author author);

    List<Author> findAll();

    Optional<Author> findAuthorById(Integer id);
    void deleteAuthorById(Integer id);
}
