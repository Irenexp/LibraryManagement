package com.barclays.service;

import com.barclays.model.Author;
import com.barclays.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> findAll();

    List<Book> findBookByAuthor(String name);

    Optional<Book> findByTitle(String title);

    public Book saveBook(Book book);

    void deleteBook(Book book);

    List<Book> findBookByGenre(String genre);

    Optional<Book> findBookById(Integer id);

}
