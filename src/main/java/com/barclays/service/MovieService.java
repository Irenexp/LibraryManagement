package com.barclays.service;

import com.barclays.model.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    List<Movie> findAll();

    List<Movie> findMovieByActor(String actor);

    List<Movie> findMovieByDirector(String director);

    Optional<Movie> findByTitle(String title);

    Movie saveMovie(Movie movie);

    void deleteMovie(Integer id);

    List<Movie> findMovieByGenre(String genre);

    Optional<Movie> findMovieById(Integer id);
}
