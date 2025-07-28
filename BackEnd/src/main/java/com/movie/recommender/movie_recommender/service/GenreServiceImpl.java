package com.movie.recommender.movie_recommender.service;

import com.movie.recommender.movie_recommender.dto.GenreDTO;
import com.movie.recommender.movie_recommender.dto.MovieDTO;
import com.movie.recommender.movie_recommender.entity.Genre;
import com.movie.recommender.movie_recommender.entity.Movie;
import com.movie.recommender.movie_recommender.repository.GenreRepository;
import com.movie.recommender.movie_recommender.repository.MovieRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepo;
    private final MovieRepository movieRepository;

    public GenreServiceImpl(GenreRepository genreRepo, MovieRepository movieRepository) {
        this.genreRepo = genreRepo;
        this.movieRepository = movieRepository;
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
    @Override
    public Page<MovieDTO> getMoviesByGenreWithYearFilter(Long genreId, Integer minYear, Integer maxYear, Pageable pageable) {
        System.out.println("genreId: " + genreId);
        System.out.println("minYear: " + minYear);
        System.out.println("maxYear: " + maxYear);
        Page<Movie> moviesPage = movieRepository.findByGenreWithYearRange(genreId, minYear, maxYear, pageable);

        return moviesPage.map(movie -> {
            MovieDTO dto = new MovieDTO();
            dto.setTconst(movie.getTconst());
            dto.setTitle(movie.getTitle());
            dto.setYear(movie.getYear());
            dto.setRating(movie.getRating());
            return dto;
        });
    }



    @Override
    public GenreDTO getGenreDetails(Long id) {
        Optional<Genre> genreOptional = getGenreById(id);
        if (genreOptional.isEmpty()) return null;
        Genre genre = genreOptional.get();
        List<Movie> movies = getMoviesByGenre(id);
        return new GenreDTO(id, genre.getName(), movies);
    }

    @Override
    public Page<Movie> getPagedMoviesByGenre(Long genreId, Pageable pageable) {
        return genreRepo.findPagedMoviesByGenreId(genreId, pageable);
    }
}
