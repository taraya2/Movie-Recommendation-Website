package com.movie.recommender.movie_recommender.dto;

import com.movie.recommender.movie_recommender.entity.Movie;
import lombok.Data;

import java.util.List;

@Data
public class DirectorDTO {
    private Long id;
    private String name;
    private List<Movie> movies;

    public DirectorDTO(Long id, String name, List<Movie> movies) {
        this.id = id;
        this.name = name;
        this.movies = movies;
    }
}
