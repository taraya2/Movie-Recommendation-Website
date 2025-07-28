package com.movie.recommender.movie_recommender.batch_service;

import com.movie.recommender.movie_recommender.entity.Genre;
import com.movie.recommender.movie_recommender.repository.GenreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Service
public class GenreBatchService {
    private final GenreRepository genreRepository;
    private final HashMap<String, Genre> genreCache = new HashMap<>();

    public GenreBatchService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Transactional
    public Set<Genre> getOrCreateGenres(String genreStr) {
        Set<Genre> genres = new HashSet<>();
        for (String part : genreStr.split(",")) {
            String name = part.trim();
            if (name.isEmpty()) continue;

            Genre genre = genreCache.get(name);
            if (genre == null) {
                genre = genreRepository.findByName(name).orElseGet(()->{
                    Genre nGenre = new Genre();
                    nGenre.setName(name);
                    return genreRepository.save(nGenre);
                });
                genreCache.put(name, genre);
            }
            genres.add(genre);
        }
        return genres;
    }
}
