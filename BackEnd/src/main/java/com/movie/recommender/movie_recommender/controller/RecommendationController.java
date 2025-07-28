package com.movie.recommender.movie_recommender.controller;

import com.movie.recommender.movie_recommender.dto.MovieDTO;
import com.movie.recommender.movie_recommender.service.RecommendationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommend")
public class RecommendationController {

    private final RecommendationService recService;
    public RecommendationController(RecommendationService recService) {
        this.recService = recService;
    }

    @GetMapping("/{tconst}")
    public List<MovieDTO> getRecommendations(@PathVariable String tconst,
                                             @RequestParam(defaultValue = "10") int limit) {
        return recService.recommendMovies(tconst, limit);
    }
}
