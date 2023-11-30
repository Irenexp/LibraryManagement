package com.barclays.controller;

import com.barclays.model.Author;
import com.barclays.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> authors = authorService.findAll();
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> searchAuthorById(@PathVariable Integer id) {
        Optional<Author> authorOptional = authorService.findAuthorById(id);
        return authorOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        Author savedAuthor = authorService.saveAuthor(author);
        return ResponseEntity.ok(savedAuthor);
    }

    @PutMapping
    public Author updatePerson(@RequestBody Author author) {
        return authorService.saveAuthor(author);
    }

    @DeleteMapping("{id}")
    public void deleteAuthor(@PathVariable int id) {
        authorService.deleteAuthorById(id);
    }


}

