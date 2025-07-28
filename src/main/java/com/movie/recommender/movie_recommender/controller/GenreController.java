package com.movie.recommender.movie_recommender.controller;

import com.movie.recommender.movie_recommender.dto.GenreDTO;
import com.movie.recommender.movie_recommender.dto.MovieDTO;
import com.movie.recommender.movie_recommender.entity.Genre;
import com.movie.recommender.movie_recommender.entity.Movie;
import com.movie.recommender.movie_recommender.service.GenreService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
//    public Page<Movie> getPagedMoviesByGenre( @PathVariable Long id,Pageable pageable) {
//        return genreService.getPagedMoviesByGenre(id, pageable);
//    }

    @GetMapping("/{id}/movies")
    public Page<MovieDTO> getMoviesByGenre(
            @PathVariable Long id,
            @RequestParam(required = false) Integer minYear,
            @RequestParam(required = false) Integer maxYear,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ) {
        System.out.println(">>> Request received with minYear = " + minYear + ", maxYear = " + maxYear);
        Pageable pageable = PageRequest.of(page, size);
        return genreService.getMoviesByGenreWithYearFilter(id, minYear, maxYear, pageable);
    }


}
