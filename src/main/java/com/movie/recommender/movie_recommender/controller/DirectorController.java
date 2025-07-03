package com.movie.recommender.movie_recommender.controller;

import com.movie.recommender.movie_recommender.entity.Director;
import com.movie.recommender.movie_recommender.entity.Movie;
import com.movie.recommender.movie_recommender.repository.DirectorRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/directors")
public class DirectorController {

    private final DirectorRepository directorRepo;

    public DirectorController(DirectorRepository directorRepo) {
        this.directorRepo = directorRepo;
    }

    @GetMapping
    public List<Director> getAllDirectors() {
        return directorRepo.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Director> getDirectorById(@PathVariable Long id) {
        return directorRepo.findById(id);
    }


    @GetMapping("/{id}/movies")
    public List<Movie> getMovieByActor(@PathVariable Long id) {
        return directorRepo.findById(id)
                .map(director -> new ArrayList<>(director.getMovies()))
                .orElseGet(ArrayList::new);
    }


}
