package com.movie.recommender.movie_recommender.repository;

import com.movie.recommender.movie_recommender.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {


}
