package com.movie.recommender.movie_recommender.dto;

import com.movie.recommender.movie_recommender.entity.Movie;
import lombok.Data;

import java.util.List;

@Data
public class ActorDTO {
    private String name;
    private Long id;
    private List<Movie> movies;

    public ActorDTO(Long id, String name, List<Movie> movies) {
        this.id = id;
        this.name = name;
        this.movies = movies;
    }
}
