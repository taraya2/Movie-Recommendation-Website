package com.movie.recommender.movie_recommender.repository;

import com.movie.recommender.movie_recommender.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {

    List<Movie> findByTitleContainingIgnoreCase(String title);
    List<Movie> findTop10ByYearOrderByRatingDesc(String year);
    List<Movie>findByGenresIgnoreCaseOrderByRatingDesc(String genre);

    List<Movie>findByDirectorIgnoreCase(String director);

}
