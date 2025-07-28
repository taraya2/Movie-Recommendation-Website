package com.movie.recommender.movie_recommender.dto;

import lombok.Data;

@Data
public class MovieDTO {
    private String tconst;
    private String title;
    private Integer year;
    private Float rating;
}

