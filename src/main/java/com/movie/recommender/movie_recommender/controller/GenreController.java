package com.movie.recommender.movie_recommender.controller;

import com.movie.recommender.movie_recommender.entity.Genre;
import com.movie.recommender.movie_recommender.entity.Movie;
import com.movie.recommender.movie_recommender.repository.GenreRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/genres")
public class GenreController {

    private final GenreRepository genreRepo;

    public GenreController(GenreRepository genreRepo) {
        this.genreRepo = genreRepo;
    }

    @GetMapping
    public List<Genre> getAllGenres() {
        return genreRepo.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Genre> getGenreById(@PathVariable Long id) {
        return genreRepo.findById(id);
    }

    @GetMapping("/{id}/movies")
    public List<Movie> getMoviesByGenre(@PathVariable Long id) {
        return genreRepo.findById(id)
                .map(genre -> new ArrayList<>(genre.getMovies()))
                .orElseGet(ArrayList::new);
    }

}
