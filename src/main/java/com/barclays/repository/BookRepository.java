package com.barclays.repository;

import com.barclays.model.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {

    @Query("SELECT b FROM Book b JOIN b.author a WHERE a.authorName = :name")
    List<Book> findByAuthorName(@Param("name") String name);
    Optional<Book> findByTitle(String title);

    List<Book> findByGenre(String genre);

}
