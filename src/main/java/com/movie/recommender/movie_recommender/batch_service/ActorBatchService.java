package com.movie.recommender.movie_recommender.batch_service;

import com.movie.recommender.movie_recommender.entity.Actor;
import com.movie.recommender.movie_recommender.repository.ActorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Service
public class ActorBatchService {
    private final ActorRepository actorRepository;
    private final HashMap<String, Actor> actorCache = new HashMap<>();

    public ActorBatchService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    @Transactional
    public Set<Actor> getOrCreateActors(String actorStr) {
        Set<Actor> actors = new HashSet<>();

        for (String part : actorStr.split("\\|")) {
            String name = part.trim();
            if (name.isEmpty()) continue;

            Actor actor = actorCache.get(name);
            if (actor == null) {
                actor = actorRepository.findByName(name).orElseGet(()->  {
                    Actor a = new Actor();
                    a.setName(name);
                    return actorRepository.save(a);
                });
                actorCache.put(name,actor);
            }
            actors.add(actor);
        }
        return actors;
    }
}
