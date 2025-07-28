package com.movie.recommender.movie_recommender.repository;

import com.movie.recommender.movie_recommender.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DirectorRepository extends JpaRepository <Director, Long>{
    Optional<Director> findByName(String name);

}
