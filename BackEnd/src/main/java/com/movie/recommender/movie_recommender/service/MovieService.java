package com.movie.recommender.movie_recommender.service;

import com.movie.recommender.movie_recommender.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MovieService {

    Page<Movie> getAllMovies(Pageable pageable);
    Optional<Movie> getMovieById(String tconst);
    Page<Movie> getMovieByTitle(String title, Pageable pageable);
    Page<Movie> topRatedByYear(Integer year, Pageable pageable);
    Page<Movie> getMoviesByDirector(String director, Pageable pageable);
    Page<Movie> getMovieByGenre(String genre, Pageable pageable);
    Page<Movie> getMoviesByYear(Integer year, Pageable pageable);
    Page<Movie> getMoviesRatingAbove( Float minRating,Pageable pageable);
    Page<Movie> getTopRatedMovies(Pageable pageable);
    Page<Movie> getMoviesByGenreAndYear(String genre, Integer year,Pageable pageable);
    Page<Movie> getMoviesByGenreAndMinRating(String genre, Float minRating,Pageable pageable);
}
