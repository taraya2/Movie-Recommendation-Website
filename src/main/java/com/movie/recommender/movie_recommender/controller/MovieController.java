package com.movie.recommender.movie_recommender.controller;

import com.movie.recommender.movie_recommender.entity.Movie;
import com.movie.recommender.movie_recommender.repository.MovieRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @GetMapping
    public Page<Movie> getAllMovies(Pageable pageable) {
        return movieRepo.findAll(pageable);
    }

    // Get /api/movies/{tconst}
    @GetMapping("/{tconst}")
    public Optional<Movie> getMovieById(@PathVariable String tconst) {
        return movieRepo.findById(tconst);
    }

    //// Search movies by title (partial match)
    // Get /movies/filter/title?title=Batman
    @GetMapping("/filter/title")
    public Page<Movie> getMovieByTitle(@RequestParam String title, Pageable pageable) {
        return movieRepo.findByTitleContainingIgnoreCase(title, pageable);
    }

    // GET /api/movies/filter/top-rated-by-year?year=2010
    @GetMapping("/filter/top-rated-by-year")
    public Page<Movie> topRatedByYear(@RequestParam Integer year, Pageable pageable) {
        return movieRepo.findTop10ByYearOrderByRatingDesc(year, pageable);
    }

    // Get /api/movies/filter/director?director=Christopher Nolan
    @GetMapping("/filter/director")
    public Page<Movie> getMoviesByDirector(@RequestParam String director, Pageable pageable) {
        return movieRepo.findByDirectorIgnoreCase(director, pageable);
    }

    ////// Search Filter Movies endpoints ///////////\
    ///

    // GET /api/movies/filter/genre/action
    @GetMapping("/filter/genre/{genre}")
    public Page<Movie> getMovieByGenre(@PathVariable String genre, Pageable pageable) {
        return movieRepo.findByGenresIgnoreCaseOrderByRatingDesc(genre, pageable);
    }

    // /api/movies/filter/year/2020
    @GetMapping("/filter/year/{year}")
    public Page<Movie> getMoviesByYear(@PathVariable Integer year, Pageable pageable) {
        return movieRepo.findMoviesByYear(year, pageable );
    }

    //// Get movies with rating above x
    @GetMapping("/filter/rating-above/{minRating}")
    public Page<Movie> getMoviesRatingAbove(@PathVariable Float minRating,
                                            Pageable pageable) {
        return movieRepo.findMoviesWithRatingAbove(minRating, pageable);
    }

    /// /api/movies/filter/top-rated?size=10
    @GetMapping("/filter/top-rated")
    public Page<Movie> getTopRatedMovies(Pageable pageable) { // fix this dont we need a size paramater but how would be edit the query to list only that amount
        return movieRepo.findTopRatedMovies(pageable);
    }

    ///  /api/movies/filter/genre-year?genre=Action&year=2020
    @GetMapping("/filter/genre-year")
    public Page<Movie> getMoviesByGenreAndYear(@RequestParam String genre,
                                               @RequestParam Integer year,
                                               Pageable pageable) {
        return movieRepo.findByGenresIgnoreCaseAndYear(genre, year, pageable);
    }

    /// /api/movies/filter/genre-rating?genre=Action&minRating=8.0
    @GetMapping("/filter/genre-rating")
    private Page<Movie> getMoviesByGenreAndMinRating(@RequestParam String genre,
                                                     @RequestParam Float minRating,
                                                     Pageable pageable) {
        return movieRepo.findMoviesByGenreIgnoreCaseAndRatingGreaterThanEqual(genre,minRating, pageable);
    }


}