package com.movie.recommender.movie_recommender.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Actor {

    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private Long id;
    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "topCast")
    private Set<Movie> movies;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return name != null && name.equals(actor.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    public Actor(String name) {
        this.name = name;
    }
}
