package com.barclays.service;

import com.barclays.model.Book;
import com.barclays.model.BorrowedBook;
import com.barclays.model.Reader;
import com.barclays.repository.BookRepository;
import com.barclays.repository.BorrowedBookRepository;
import com.barclays.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorrowedBookService {
    @Autowired
    private BorrowedBookRepository borrowedBookRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ReaderRepository readerRepository;

    @Transactional
    public BorrowedBook borrowBook(Integer bookId, Integer readerId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        Reader reader = readerRepository.findById(readerId)
                .orElseThrow(() -> new RuntimeException("Reader not found"));

        boolean isBookCurrentlyBorrowed = borrowedBookRepository.existsByBookIdAndReturnDateIsNull(bookId);
        if(isBookCurrentlyBorrowed) {
            throw new RuntimeException("Book is currently borrowed and not available");
        }

        BorrowedBook borrowedBook = new BorrowedBook();
        borrowedBook.setBook(book);
        borrowedBook.setReader(reader);
        borrowedBook.setBorrowDate(LocalDate.now());
        borrowedBook.setReturnDate(null);

        return borrowedBookRepository.save(borrowedBook);
    }

    public BorrowedBook returnBook(Integer borrowedBookId) {
        BorrowedBook borrowedBook = borrowedBookRepository.findById(borrowedBookId)
                .orElseThrow(() -> new RuntimeException("Borrowed book record not found"));

        borrowedBook.setReturnDate(LocalDate.now());

        return borrowedBookRepository.save(borrowedBook);
    }

    public List<BorrowedBook> getCurrentBorrowedBooks() {
        return borrowedBookRepository.findByReturnDateIsNull();
    }


    public List<Book> findBooksBorrowedByReader(Integer readerId) {
        List<BorrowedBook> borrowedBooks = borrowedBookRepository.findByReaderId(readerId);
        return borrowedBooks.stream().map(BorrowedBook::getBook).toList();
    }
}
