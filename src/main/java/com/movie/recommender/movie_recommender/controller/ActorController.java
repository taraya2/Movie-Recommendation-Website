package com.movie.recommender.movie_recommender.controller;

import com.movie.recommender.movie_recommender.entity.Actor;
import com.movie.recommender.movie_recommender.entity.Movie;
import com.movie.recommender.movie_recommender.repository.ActorRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/actors")
public class ActorController {

    private final ActorRepository actorRepo;

    public ActorController(ActorRepository actorRepo) {
        this.actorRepo = actorRepo;
    }

    @GetMapping()
    public List<Actor> getAllActors() {
        return actorRepo.findAll();
    }

    // Get /api/actors/{id}
    @GetMapping("/{id}")
    public Optional<Actor> getActorById(@PathVariable Long id) {
        return actorRepo.findById(id);
    }

    // Get /api/actors/{id}/movies
    @GetMapping("/{id}/movies")
    public List<Movie> getMoviesByActor(@PathVariable Long id) {
        return actorRepo.findById(id)
                .map(actor -> new ArrayList<>(actor.getMovies()))
                .orElseGet(ArrayList::new);
    }

}
