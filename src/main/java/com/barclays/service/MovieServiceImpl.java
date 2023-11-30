package com.barclays.service;

import com.barclays.model.Movie;
import com.barclays.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService{

    @Autowired
    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Movie> findAll(){
        List<Movie> movies = new ArrayList<>();
        Iterable<Movie> movieIts = movieRepository.findAll();
        movieIts.forEach((movies::add));
        return movies;
    }

    @Override
    public List<Movie> findMovieByActor(String actor) {
        return movieRepository.findByActor(actor);
    }

    @Override
    public List<Movie> findMovieByDirector(String director) {
        return movieRepository.findByDirector(director);
    }

    @Override
    public Optional<Movie> findByTitle(String title){
        return movieRepository.findByTitle(title);
    }

    @Override
    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public void deleteMovie(Integer id) {movieRepository.deleteById(id);}


    @Override
    public List<Movie> findMovieByGenre(String genre) {
        return movieRepository.findByGenre(genre);
    }

    @Override
    public Optional<Movie> findMovieById(Integer id) {
        return movieRepository.findById(id);
    }

}
