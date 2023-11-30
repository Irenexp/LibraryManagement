package com.barclays.controller;

import com.barclays.model.Book;
import com.barclays.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book>books=bookService.findAll();
        return ResponseEntity.ok(books);

    }

    @GetMapping("/searchByTitle")
    public ResponseEntity<Book> searchByTitle(@RequestParam String title) {
        Optional<Book> book = bookService.findByTitle(title);
        return book.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/searchByAuthor")
    public ResponseEntity<List<Book>> searchByAuthor(@RequestParam("authorName") String name) {
        List<Book> books = bookService.findBookByAuthor(name);
        if (books.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(books);
    }

    @GetMapping("/searchByGenre")
    public ResponseEntity<List<Book>> searchByGenre(@RequestParam String genre) {
        return ResponseEntity.ok(bookService.findBookByGenre(genre));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> searchById(@PathVariable Integer id) {
        Optional<Book> bookOptional = bookService.findBookById(id);
        return bookOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Book createBook(@RequestBody Book book){
        return bookService.saveBook(book);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Integer id, @RequestBody Book bookDetails) {
        Optional<Book> bookOptional = bookService.findBookById(id);

        if (bookOptional.isPresent()) {
            Book bookToUpdate = bookOptional.get();

            bookToUpdate.setTitle(bookDetails.getTitle());
            bookToUpdate.setPublisher(bookDetails.getPublisher());
            bookToUpdate.setGenre(bookDetails.getGenre());

            Book updatedBook = bookService.saveBook(bookToUpdate);
            return ResponseEntity.ok(updatedBook);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public void deleteBook(@RequestBody Book book) {
        bookService.deleteBook(book);
    }

}