package com.movie.recommender.movie_recommender.service;

import com.movie.recommender.movie_recommender.entity.Movie;
import com.movie.recommender.movie_recommender.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    private final MovieRepository movieRepo;
    public RecommendationServiceImpl(MovieRepository movieRepo) {
        this.movieRepo = movieRepo;
    }
    @Override
    public List<Movie> recommendMovies(String tconst, int limit) {
        Optional<Movie> optionalMovie = movieRepo.findById(tconst);
        if (optionalMovie.isEmpty()) return Collections.emptyList();

        Movie base = optionalMovie.get();

        // grab essential details
        Set<String> genreNames = base.getGenres().stream().map(g-> g.getName().toLowerCase()).collect(Collectors.toSet());
        Set<String> directorNames = base.getDirectors().stream().map(d -> d.getName().toLowerCase()).collect(Collectors.toSet());
        Set<String> actorNames = base.getTopCast().stream().map(g -> g.getName().toLowerCase()).collect(Collectors.toSet());

        List<Movie> allMovies = movieRepo.findAll();
        return allMovies.stream()
                .filter(m -> !m.getTconst().equals(tconst))
                .sorted(Comparator.comparingInt(m -> similarityScore(m, genreNames, directorNames,actorNames)))
                .limit(limit)
                .collect(Collectors.toList());
    }

    private int similarityScore(Movie movie, Set<String> genreNames,
                                Set<String> directorNames, Set<String> actorNames) {
        // search through movie and look for matches
        int score = 0;

        for (var g : movie.getGenres()) {
            if (genreNames.contains(g.getName().toLowerCase())) score += 2;
        }
        for (var d : movie.getDirectors()) {
            if (directorNames.contains(d.getName().toLowerCase())) score += 3;
        }
        for (var a : movie.getTopCast()) {
            if (actorNames.contains(a.getName().toLowerCase())) score += 1;
        }
        return score;
    }
}
