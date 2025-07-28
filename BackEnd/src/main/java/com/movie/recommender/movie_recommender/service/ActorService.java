package com.movie.recommender.movie_recommender.service;

import com.movie.recommender.movie_recommender.dto.ActorDTO;
import com.movie.recommender.movie_recommender.entity.Actor;
import com.movie.recommender.movie_recommender.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface ActorService {

    Page<Actor> getAllActors(Pageable pageable);
    Optional<Actor> getActorById(Long id);
    List<Movie> getMoviesByActor(Long id);
    ActorDTO getActorDetails(Long id);
}
