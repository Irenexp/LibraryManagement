package com.barclays.repository;

import com.barclays.model.Book;
import com.barclays.model.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Integer> {
    @Query("SELECT m FROM Movie m JOIN m.leadActorList l WHERE l.name = :name")
    List<Movie> findByActor(@Param("name") String name);

    List<Movie> findByDirector(String director);
    Optional<Movie> findByTitle(String title);

    List<Movie> findByGenre(String genre);

}
