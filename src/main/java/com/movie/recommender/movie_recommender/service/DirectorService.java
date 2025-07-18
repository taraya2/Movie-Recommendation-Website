package com.movie.recommender.movie_recommender.service;

import com.movie.recommender.movie_recommender.dto.DirectorDTO;
import com.movie.recommender.movie_recommender.entity.Director;
import com.movie.recommender.movie_recommender.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DirectorService {

    Page<Director> getAllDirectors(Pageable pageable);
    Optional<Director> getDirectorById(Long id);
    List<Movie> getMovieByDirectorId( Long id);
    DirectorDTO getDirectorDetails(Long id);
}
