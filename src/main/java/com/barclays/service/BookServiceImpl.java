package com.barclays.service;

import com.barclays.model.Book;
import com.barclays.model.BorrowedBook;
import com.barclays.repository.BookRepository;
import com.barclays.repository.BorrowedBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private final BookRepository bookRepository;

    @Autowired
    private final BorrowedBookRepository borrowedBookRepository;

    public BookServiceImpl(BookRepository bookRepository, BorrowedBookRepository borrowedBookRepository) {
        this.bookRepository = bookRepository;
        this.borrowedBookRepository = borrowedBookRepository;
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
    @Transactional
    public void deleteBook(Book book) {
        if (book == null || book.getId() == null) {
            throw new IllegalArgumentException("Book or Book ID must not be null");
        }

        Integer bookId = book.getId();

        List<BorrowedBook> borrowedBooks = borrowedBookRepository.findByBookId(bookId);

        if (!borrowedBooks.isEmpty()) {
            throw new IllegalStateException("Cannot delete book as it has borrowed records.");
        }

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
