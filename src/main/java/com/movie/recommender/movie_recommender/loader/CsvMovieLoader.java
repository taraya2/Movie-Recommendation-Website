package com.movie.recommender.movie_recommender.loader;

import com.movie.recommender.movie_recommender.entity.Movie;
import com.movie.recommender.movie_recommender.repository.MovieRepository;
import com.movie.recommender.movie_recommender.service.ActorBatchService;
import com.movie.recommender.movie_recommender.service.DirectorBatchService;
import com.movie.recommender.movie_recommender.service.GenreBatchService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class CsvMovieLoader {

    private final MovieRepository movieRepo;
    private final GenreBatchService genreBatchService;
    private final DirectorBatchService directorBatchService;
    private final ActorBatchService actorBatchService;

    private static final int BATCH_SIZE = 50;

    public CsvMovieLoader(MovieRepository movieRepo,
                          GenreBatchService genreBatchService,
                          DirectorBatchService directorBatchService,
                          ActorBatchService actorBatchService) {
        this.movieRepo = movieRepo;
        this.genreBatchService = genreBatchService;
        this.directorBatchService = directorBatchService;
        this.actorBatchService = actorBatchService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadCsv() throws IOException {
        if (movieRepo.count() > 0 ) {
            System.out.println("Movies already exist in the database. Skipping CSV import.");
            return;
        }
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("data/movies_clean.csv");
            if (is == null) {
                System.out.println("CSV file not found");
                return;
            }

            System.out.println("Starting CSV import");
            Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withIgnoreSurroundingSpaces());

            long recordNumber = 0;
            List<Movie> movieBatch = new ArrayList<>();

            for (CSVRecord record : csvParser) {
                recordNumber++;

                try {
                    String tconst = record.get("tconst");

                    // Skip if already exists
                    if (movieRepo.existsById(tconst)) continue;

                    String title = record.get("title");
                    Integer year = Integer.valueOf(record.get("year"));
                    Float rating = Float.valueOf(record.get("rating"));
                    Integer numVotes = Integer.valueOf(record.get("numVotes"));

                    Movie m = new Movie();
                    m.setTconst(tconst);
                    m.setTitle(title);
                    m.setYear(year);
                    m.setRating(rating);
                    m.setNumVotes(numVotes);
                    m.setGenres(genreBatchService.getOrCreateGenres(record.get("genres")));
                    m.setDirectors(directorBatchService.getOrCreateDirectors(record.get("directors")));
                    m.setTopCast(actorBatchService.getOrCreateActors(record.get("topCast")));

                    movieBatch.add(m);

                    if (movieBatch.size() >= BATCH_SIZE) {
                        movieRepo.saveAll(movieBatch);
                        movieRepo.flush();
                        movieBatch.clear();
                    }

                } catch (Exception e) {
                    System.err.println("Error processing record #" + recordNumber + ": " + record);
                    e.printStackTrace();
                }

                if (recordNumber % 1000 == 0) {
                    System.out.println("Processed " + recordNumber + " records...");
                }
            }

            // Save remaining movies
            if (!movieBatch.isEmpty()) {
                movieRepo.saveAll(movieBatch);
                movieRepo.flush();
                movieBatch.clear();
            }

            System.out.println("CSV imported");

        } catch (Exception e) {
            System.err.println("Failed to load CSV");
            e.printStackTrace();
        }
    }
}
