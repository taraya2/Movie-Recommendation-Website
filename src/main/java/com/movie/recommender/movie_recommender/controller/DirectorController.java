package com.movie.recommender.movie_recommender.controller;

import com.movie.recommender.movie_recommender.entity.Director;
import com.movie.recommender.movie_recommender.entity.Movie;
import com.movie.recommender.movie_recommender.service.DirectorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/directors")
public class DirectorController {

    private final DirectorService directorService;

    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @GetMapping
    public Page<Director> getAllDirectors(Pageable pageable) {
        return directorService.getAllDirectors(pageable);
    }

    @GetMapping("/{id}")
    public Optional<Director> getDirectorById(@PathVariable Long id) {
        return directorService.getDirectorById(id);
    }


    @GetMapping("/{id}/movies")
    public List<Movie> getMovieByActor(@PathVariable Long id) {
        return directorService.getMovieByDirectorId(id);
    }
}
