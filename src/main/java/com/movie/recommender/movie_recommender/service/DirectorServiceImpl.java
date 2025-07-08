package com.movie.recommender.movie_recommender.service;

import com.movie.recommender.movie_recommender.entity.Director;
import com.movie.recommender.movie_recommender.entity.Movie;
import com.movie.recommender.movie_recommender.repository.DirectorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DirectorServiceImpl implements DirectorService{

    private final DirectorRepository directorRepo;
    public DirectorServiceImpl(DirectorRepository directorRepo) {
        this.directorRepo = directorRepo;
    }

    @Override
    public Page<Director> getAllDirectors(Pageable pageable) {
        return directorRepo.findAll(pageable);
    }

    @Override
    public Optional<Director> getDirectorById(Long id) {
        return directorRepo.findById(id);
    }

    @Override
    public List<Movie> getMovieByDirectorId(Long id) {
        return directorRepo.findById(id)
                .map(director -> new ArrayList<>(director.getMovies()))
                .orElseGet(ArrayList::new);
    }
}
