package com.movie.recommender.movie_recommender.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    @Id
    private String tconst;
    private String title;
    private Integer year;
    private Float rating;
    private Integer numVotes;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "movie_genres", //name of the join table in PostGres
            joinColumns = @JoinColumn(name = "tconst"), // references Movie.tconst
            inverseJoinColumns = @JoinColumn(name = "genre_id") // the other entity's FK column
    )
    private Set<Genre> genres;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "movie_directors",
            joinColumns = @JoinColumn(name = "tconst"),
            inverseJoinColumns = @JoinColumn(name = "director_id")
    )
    private Set<Director> directors;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "movie_actors",
            joinColumns = @JoinColumn(name = "tconst"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private Set<Actor> topCast;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return tconst != null && tconst.equals(movie.tconst);
    }

    @Override
    public int hashCode() {
        return tconst != null ? tconst.hashCode() : 0;
    }

}
