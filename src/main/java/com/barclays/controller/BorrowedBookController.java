package com.barclays.controller;

import com.barclays.dto.BorrowedBookDTO;
import com.barclays.model.BorrowedBook;
import com.barclays.service.BorrowedBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrowBooks")
public class BorrowedBookController {
    private final BorrowedBookService borrowedBookService;

    @Autowired
    public BorrowedBookController(BorrowedBookService borrowedBookService) {
        this.borrowedBookService = borrowedBookService;
    }

    @PostMapping("/borrow")
    public ResponseEntity<BorrowedBookDTO> borrowBook(@RequestParam Integer bookId, @RequestParam Integer readerId) {
        BorrowedBook borrowedBook = borrowedBookService.borrowBook(bookId, readerId);
        BorrowedBookDTO borrowedBookDTO = convertToDTO(borrowedBook);
        return ResponseEntity.ok(borrowedBookDTO);
    }

    private BorrowedBookDTO convertToDTO(BorrowedBook borrowedBook) {
        BorrowedBookDTO dto = new BorrowedBookDTO();
        dto.setId(borrowedBook.getId());
        dto.setBookId(borrowedBook.getBook() != null ? borrowedBook.getBook().getId() : null);
        dto.setReaderId(borrowedBook.getReader() != null ? borrowedBook.getReader().getId() : null);
        dto.setBorrowDate(borrowedBook.getBorrowDate());
        dto.setReturnDate(borrowedBook.getReturnDate());
        return dto;
    }

    @PostMapping("/return/{borrowedBookId}")
    public ResponseEntity<BorrowedBook> returnBook(@PathVariable Integer borrowedBookId) {
        BorrowedBook returnedBook = borrowedBookService.returnBook(borrowedBookId);
        return ResponseEntity.ok(returnedBook);
    }

    @GetMapping("/current")
    public ResponseEntity<List<BorrowedBook>> getCurrentBorrowedBooks() {
        List<BorrowedBook> borrowedBooks = borrowedBookService.getCurrentBorrowedBooks();
        return ResponseEntity.ok(borrowedBooks);
    }

}
