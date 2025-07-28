package com.movie.recommender.movie_recommender.controller;

import com.movie.recommender.movie_recommender.dto.GenreDTO;
import com.movie.recommender.movie_recommender.entity.Genre;
import com.movie.recommender.movie_recommender.entity.Movie;
import com.movie.recommender.movie_recommender.service.GenreService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/genres")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public Page<Genre> getAllGenres(Pageable pageable) {
        return genreService.getAllGenres(pageable);
    }

    @GetMapping("/{id}") /// Lists eveything movie not optimized for frontend
    public ResponseEntity<GenreDTO> getGenreDetails(@PathVariable Long id) {
        GenreDTO genreDTO = genreService.getGenreDetails(id);
        if (genreDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(genreDTO);
    }

//    @GetMapping("/{id}/movies")
//    public List<Movie> getMoviesByGenre(@PathVariable Long id) {
//        return genreService.getMoviesByGenre(id);
//    }

    @GetMapping("/{id}/movies")
    public Page<Movie> getPagedMoviesByGenre( @PathVariable Long id,Pageable pageable) {
        return genreService.getPagedMoviesByGenre(id, pageable);
    }


}
