package com.movie.recommender.movie_recommender.service;

import com.movie.recommender.movie_recommender.entity.Director;
import com.movie.recommender.movie_recommender.repository.DirectorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Service
public class DirectorBatchService {
    private final DirectorRepository directorRepository;
    private final HashMap<String, Director> directorCache = new HashMap<>();

    public DirectorBatchService(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    @Transactional
    public Set<Director> getOrCreateDirectors(String directorStr) {
        Set<Director> directors = new HashSet<>();
        for (String part : directorStr.split("\\|")) {
            String name = part.trim();

            if(name.isEmpty()) continue;
            Director director = directorCache.get(name);
            if (director == null) {
                director = directorRepository.findByName(name).orElseGet(()-> {
                    Director d = new Director();
                    d.setName(name);
                    return directorRepository.save(d);
                });
                directorCache.put(name, director);
            }
            directors.add(director);
        }
        return directors;
    }
}
