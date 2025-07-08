package com.movie.recommender.movie_recommender.service;

import com.movie.recommender.movie_recommender.entity.Movie;
import com.movie.recommender.movie_recommender.repository.MovieRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepo;

    public MovieServiceImpl(MovieRepository movieRepo) {
        this.movieRepo = movieRepo;
    }

    @Override
    public Page<Movie> getAllMovies(Pageable pageable) {
        return movieRepo.findAll(pageable);
    }

    @Override
    public Optional<Movie> getMovieById(String tconst) {
        return movieRepo.findById(tconst);
    }

    @Override
    public Page<Movie> getMovieByTitle(String title, Pageable pageable) {
        return movieRepo.findByTitleContainingIgnoreCase(title,pageable);
    }

    @Override
    public Page<Movie> topRatedByYear(Integer year, Pageable pageable) {
        return movieRepo.findTop10ByYearOrderByRatingDesc(year,pageable);
    }

    @Override
    public Page<Movie> getMoviesByDirector(String director, Pageable pageable) {
        return movieRepo.findByDirectorIgnoreCase(director,pageable);
    }

    @Override
    public Page<Movie> getMovieByGenre(String genre, Pageable pageable) {
        return movieRepo.findByGenresIgnoreCaseOrderByRatingDesc(genre,pageable);
    }

    @Override
    public Page<Movie> getMoviesByYear(Integer year, Pageable pageable) {
        return movieRepo.findMoviesByYear(year, pageable);
    }

    @Override
    public Page<Movie> getMoviesRatingAbove(Float minRating, Pageable pageable) {
        return movieRepo.findMoviesWithRatingAbove(minRating, pageable);
    }

    @Override
    public Page<Movie> getTopRatedMovies(Pageable pageable) {
        return movieRepo.findTopRatedMovies(pageable);
    }

    @Override
    public Page<Movie> getMoviesByGenreAndYear(String genre, Integer year, Pageable pageable) {
        return movieRepo.findByGenresIgnoreCaseAndYear(genre, year, pageable);
    }

    @Override
    public Page<Movie> getMoviesByGenreAndMinRating(String genre, Float minRating, Pageable pageable) {
        return movieRepo.findMoviesByGenreIgnoreCaseAndRatingGreaterThanEqual(genre,minRating, pageable);
    }
}
