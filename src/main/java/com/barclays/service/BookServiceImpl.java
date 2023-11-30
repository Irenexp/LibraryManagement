package com.barclays.service;

import com.barclays.model.Book;
import com.barclays.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAll(){
        List<Book> books = new ArrayList<>();
        Iterable<Book> bookIts = bookRepository.findAll();
        bookIts.forEach((books::add));
        return books;
    }
    @Override
    public List<Book> findBookByAuthor(String author) {
        return bookRepository.findByAuthorName(author);
    }

    @Override
    public Optional<Book> findByTitle(String title){
        return bookRepository.findByTitle(title);
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Book book) {
        bookRepository.delete(book);
    }

    @Override
    public List<Book> findBookByGenre(String genre) {
        return bookRepository.findByGenre(genre);
    }

    @Override
    public Optional<Book> findBookById(Integer id) {
        return bookRepository.findById(id);
    }


}
