package com.movie.recommender.movie_recommender.service;

import com.movie.recommender.movie_recommender.dto.MovieDTO;
import com.movie.recommender.movie_recommender.entity.Movie;
import com.movie.recommender.movie_recommender.repository.MovieRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public List<MovieDTO> recommendMovies(String tconst, int limit) {
        Optional<Movie> optionalMovie = movieRepo.findById(tconst);
        if (optionalMovie.isEmpty()) return Collections.emptyList();

        Movie base = optionalMovie.get();
        Set<String> genreNames = base.getGenres().stream()
                .map(g -> g.getName().toLowerCase()).collect(Collectors.toSet());

        // Fetch a smaller, relevant candidate pool
        Pageable top100 = PageRequest.of(0, 100);
        List<Movie> candidates = movieRepo.findByGenres(new ArrayList<>(genreNames), top100);

        return candidates.stream()
                .filter(m -> !m.getTconst().equals(tconst))
                .sorted(Comparator.comparingInt(m -> -similarityScore(m, genreNames))) // sort DESC
                .limit(limit)
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private int similarityScore(Movie movie, Set<String> genreNames) {
        int score = 0;
        for (var g : movie.getGenres()) {
            if (genreNames.contains(g.getName().toLowerCase())) score += 2;
        }
        return score;
    }

    private MovieDTO mapToDto(Movie m) {
        MovieDTO dto = new MovieDTO();
        dto.setTconst(m.getTconst());
        dto.setTitle(m.getTitle());
        dto.setYear(m.getYear());
        dto.setRating(m.getRating());
        return dto;
    }
}
