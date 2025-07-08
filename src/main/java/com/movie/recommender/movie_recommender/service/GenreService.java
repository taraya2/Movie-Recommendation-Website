package com.movie.recommender.movie_recommender.service;

import com.movie.recommender.movie_recommender.entity.Genre;
import com.movie.recommender.movie_recommender.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    Page<Genre> getAllGenres(Pageable pageable);
    Optional<Genre> getGenreById(Long id);
    List<Movie> getMoviesByGenre(Long id);
}
