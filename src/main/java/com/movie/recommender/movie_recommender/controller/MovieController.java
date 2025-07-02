package com.movie.recommender.movie_recommender.controller;

import com.movie.recommender.movie_recommender.entity.Actor;
import com.movie.recommender.movie_recommender.entity.Movie;
import com.movie.recommender.movie_recommender.repository.MovieRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieRepository movieRepo;

    public MovieController(MovieRepository movieRepo) {
        this.movieRepo = movieRepo;
    }

    // Get /api/movies/{tconst}
    @GetMapping("/{tconst}")
    public Optional<Movie> getMovieById(@PathVariable String tconst) {
        return movieRepo.findById(tconst);
    }

    // Get /movies/search?title=Batman
    @GetMapping("/search")
    public List<Movie> getMovieByTitle(@RequestParam String title) {
        return movieRepo.findByTitleContainingIgnoreCase(title);
    }

    // GET /api/movies/top-rated?year=2010
    @GetMapping("/top-rated")
    public List<Movie> topRatedByYear(@RequestParam String year) {
        return movieRepo.findTop10ByYearOrderByRatingDesc(year);
    }
    // GET /api/movies/recommendations?genre=Action
    @GetMapping("/recommendations")
    public List<Movie> recommendByGenre(@RequestParam String genre) {
        return movieRepo.findByGenresIgnoreCaseOrderByRatingDesc(genre);
    }



}
