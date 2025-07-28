package com.movie.recommender.movie_recommender.repository;

import com.movie.recommender.movie_recommender.entity.Genre;
import com.movie.recommender.movie_recommender.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    Optional<Genre> findByName(String name);

    @Query("SELECT m FROM Movie m JOIN m.genres g WHERE g.id = :genreId")
    Page<Movie> findPagedMoviesByGenreId(@Param("genreId") Long genreId, Pageable pageable);


}
