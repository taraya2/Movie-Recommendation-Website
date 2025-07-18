package com.movie.recommender.movie_recommender.repository;

import com.movie.recommender.movie_recommender.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {

    Page<Movie> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<Movie> findTop10ByYearOrderByRatingDesc(Integer year, Pageable pageable);

    @Query("SELECT m FROM Movie m JOIN m.genres g WHERE LOWER(g.name) = LOWER(:genre) ORDER by m.rating DESC")
    Page<Movie>findByGenresIgnoreCaseOrderByRatingDesc(@Param("genre") String genre, Pageable pageable);

    @Query("SELECT m FROM Movie m JOIN m.directors d WHERE LOWER(d.name) = LOWER(:name)")
    Page<Movie>findByDirectorIgnoreCase(@Param("name") String name, Pageable pageable);

    @Query("SELECT m FROM Movie m WHERE m.year = :year")
    Page<Movie> findMoviesByYear(Integer year, Pageable pageable);

    @Query("SELECT m FROM Movie m WHERE m.rating > :minRating")
    Page<Movie> findMoviesWithRatingAbove(@Param("minRating") Float minRating, Pageable pageable);

    @Query("SELECT m FROM Movie m ORDER BY m.rating DESC")
    Page<Movie> findTopRatedMovies(Pageable pageable);

    @Query("SELECT m FROM Movie m JOIN m.genres g WHERE LOWER(g.name) = LOWER(:genre) AND m.year = :year")
    Page<Movie> findByGenresIgnoreCaseAndYear(@Param("genre") String genre, @Param("year") Integer year, Pageable pageable);

    @Query("SELECT m FROM Movie m JOIN m.genres g WHERE LOWER(g.name) = LOWER(:genre) AND m.rating >= :minRating")
    Page<Movie> findMoviesByGenreIgnoreCaseAndRatingGreaterThanEqual(@Param("genre")String genre, @Param("minRating") Float minRating, Pageable pageable);

    @Query("SELECT DISTINCT m FROM Movie m JOIN m.genres g WHERE LOWER(g.name) IN :genreNames")
    List<Movie> findByGenres(@Param("genreNames") List<String> genreNames, Pageable pageable);

}
