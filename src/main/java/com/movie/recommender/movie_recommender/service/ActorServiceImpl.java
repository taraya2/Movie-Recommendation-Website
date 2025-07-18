package com.movie.recommender.movie_recommender.service;

import com.movie.recommender.movie_recommender.dto.ActorDTO;
import com.movie.recommender.movie_recommender.entity.Actor;
import com.movie.recommender.movie_recommender.entity.Movie;
import com.movie.recommender.movie_recommender.repository.ActorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ActorServiceImpl implements ActorService{

    private final ActorRepository actorRepo;
    public ActorServiceImpl(ActorRepository actorRepo) {
        this.actorRepo = actorRepo;
    }

    @Override
    public Page<Actor> getAllActors(Pageable pageable) {
        return actorRepo.findAll(pageable);
    }

    @Override
    public Optional<Actor> getActorById(Long id) {
        return actorRepo.findById(id);
    }

    @Override
    public List<Movie> getMoviesByActor(Long id) {
        return actorRepo.findById(id)
                .map(actor -> new ArrayList<>(actor.getMovies()))
                .orElseGet(ArrayList::new);
    }

    @Override
    public ActorDTO getActorDetails(Long id) {
        Optional<Actor> actorOpt = actorRepo.findById(id);
        if (actorOpt.isEmpty()) return null;

        Actor actor = actorOpt.get();
        List<Movie> movies = getMoviesByActor(id);

        return new ActorDTO(actor.getId(), actor.getName(), movies);
    }
}
