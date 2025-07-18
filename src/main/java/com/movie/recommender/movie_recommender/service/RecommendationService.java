package com.movie.recommender.movie_recommender.service;

import com.movie.recommender.movie_recommender.dto.MovieDTO;

import java.util.List;

public interface RecommendationService {

    List<MovieDTO> recommendMovies(String tconst, int limit);

}
