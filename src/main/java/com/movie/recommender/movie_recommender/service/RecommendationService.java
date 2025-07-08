package com.movie.recommender.movie_recommender.service;

import com.movie.recommender.movie_recommender.entity.Movie;

import java.util.List;

public interface RecommendationService {

    List<Movie> recommendMovies(String tconst, int limit);

}
