package com.movie.recommender.movie_recommender.service;

import com.movie.recommender.movie_recommender.entity.Genre;
import com.movie.recommender.movie_recommender.entity.Movie;
import com.movie.recommender.movie_recommender.repository.GenreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepo;

    public GenreServiceImpl(GenreRepository genreRepo) {
        this.genreRepo = genreRepo;
    }

    @Override
    public Page<Genre> getAllGenres(Pageable pageable) {
        return genreRepo.findAll(pageable);
    }

    @Override
    public Optional<Genre> getGenreById(Long id) {
        return genreRepo.findById(id);
    }

    @Override
    public List<Movie> getMoviesByGenre(Long id) {
        return genreRepo.findById(id)
                .map(genre -> new ArrayList<>(genre.getMovies()))
                .orElseGet(ArrayList::new);
    }
}
