package com.barclays.controller;

import com.barclays.model.Book;
import com.barclays.model.Reader;
import com.barclays.service.BorrowedBookService;
import com.barclays.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/readers")
public class ReaderController {

    @Autowired
    private final ReaderService readerService;
    @Autowired
    private final BorrowedBookService borrowedBookService;

    public ReaderController(ReaderService readerService, BorrowedBookService borrowedBookService) {
        this.readerService = readerService;
        this.borrowedBookService = borrowedBookService;
    }

    @GetMapping
    public ResponseEntity<List<Reader>> getAllReaders() {
        List<Reader> readers = readerService.findAll();
        return ResponseEntity.ok(readers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reader> getReaderById(@PathVariable Integer id) {
        Reader reader = readerService.findReaderById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reader not found"));
        return ResponseEntity.ok(reader);
    }

    @GetMapping("/{id}/borrowedBooks")
    public ResponseEntity<List<Book>> getBorrowedBooksByReader(@PathVariable Integer id) {
        List<Book> borrowedBooks = borrowedBookService.findBooksBorrowedByReader(id);
        return ResponseEntity.ok(borrowedBooks);
    }

    @PostMapping
    public ResponseEntity<Reader> createReader(@RequestBody Reader reader) {
        Reader savedReader = readerService.saveReader(reader);
        return new ResponseEntity<>(savedReader, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reader> updateReader(@PathVariable Integer id, @RequestBody Reader readerDetails) {
        Reader existingReader = readerService.findReaderById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reader not found"));
        existingReader.setName(readerDetails.getName());

        Reader updatedReader = readerService.saveReader(existingReader);
        return ResponseEntity.ok(updatedReader);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReader(@PathVariable Integer id) {
        readerService.deleteReaderById(id);
        return ResponseEntity.noContent().build();
    }

}
