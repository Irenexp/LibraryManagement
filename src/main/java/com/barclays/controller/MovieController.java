package com.barclays.controller;
import com.barclays.model.Movie;
import com.barclays.repository.MovieRepository;
import com.barclays.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;
    private final MovieRepository movieRepository;

    @Autowired
    public MovieController(MovieService movieService, MovieRepository movieRepository) {
        this.movieService = movieService;
        this.movieRepository = movieRepository;
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movies = movieService.findAll();
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/searchByTitle")
    public ResponseEntity<Movie> getMovieByTitle(@RequestParam String title) {
        Optional<Movie> movie = movieService.findByTitle(title);
        return movie.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/searchByActor")
    public ResponseEntity<List<Movie>> getMoviesByActor(@RequestParam String actor) {
        List<Movie> movies = movieService.findMovieByActor(actor);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/searchByDirector")
    public ResponseEntity<List<Movie>> getMoviesByDirector(@RequestParam String director) {
        List<Movie> movies = movieService.findMovieByDirector(director);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/searchByGenre")
    public ResponseEntity<List<Movie>> getMoviesByGenre(@RequestParam String genre) {
        List<Movie> movies = movieService.findMovieByGenre(genre);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Integer id) {
        Optional<Movie> movie = movieService.findMovieById(id);
        return movie.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        Movie savedMovie = movieService.saveMovie(movie);
        return ResponseEntity.ok(savedMovie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Integer id, @RequestBody Movie movieDetails) {
        Optional<Movie> movieOptional = movieService.findMovieById(id);
        if (movieOptional.isPresent()) {
            Movie movieToUpdate = movieOptional.get();
            movieToUpdate.setTitle(movieDetails.getTitle());
            movieToUpdate.setDirector(movieDetails.getDirector());
            movieToUpdate.setGenre(movieDetails.getGenre());

            Movie updatedMovie = movieService.saveMovie(movieToUpdate);
            return ResponseEntity.ok(updatedMovie);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Integer id) {
        boolean exists = movieRepository.existsById(id);
        if (exists) {
            movieService.deleteMovie(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
