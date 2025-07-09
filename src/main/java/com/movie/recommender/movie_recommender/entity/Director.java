package com.movie.recommender.movie_recommender.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Director {


    @Id
    @SequenceGenerator(
            name = "director_sequence",
            sequenceName = "director_sequence"
    )
    @GeneratedValue(
            generator = "director_sequence",
            strategy = GenerationType.AUTO
    )
    private Long id;
    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "directors")
    @JsonIgnore // prevents infinite recursion
    private Set<Movie> movies;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Director director = (Director) o;
        return name != null && name.equals(director.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    public Director(String name) {
        this.name = name;
    }

}
