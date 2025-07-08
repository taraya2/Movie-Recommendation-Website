package com.movie.recommender.movie_recommender.controller;

import com.movie.recommender.movie_recommender.entity.Actor;
import com.movie.recommender.movie_recommender.entity.Movie;
import com.movie.recommender.movie_recommender.repository.ActorRepository;
import com.movie.recommender.movie_recommender.service.ActorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/actors")
public class ActorController {

    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping()
    public Page<Actor> getAllActors(Pageable pageable) {
        return actorService.getAllActors(pageable);
    }

    // Get /api/actors/{id}
    @GetMapping("/{id}")
    public Optional<Actor> getActorById(@PathVariable Long id) {
        return actorService.getActorById(id);
    }

    // Get /api/actors/{id}/movies
    @GetMapping("/{id}/movies")
    public List<Movie> getMoviesByActor(@PathVariable Long id) {
        return actorService.getMoviesByActor(id);
    }

}
