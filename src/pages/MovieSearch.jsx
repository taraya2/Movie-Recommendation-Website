// src/pages/MovieSearch.jsx
import { useEffect, useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import Layout from "../components/Layout";
import {
  pageStyle,
  cardStyle,
  inputStyle,
  buttonStyle,
  movieCardStyle,
  movieLinkStyle,
  filterGroupStyle,
  sectionTitleStyle,
  listStyle
} from "../styles/sharedStyles";

const OMDB_API_KEY = "3170ca1b";

const MovieSearch = () => {
  const [title, setTitle] = useState("");
  const [genre, setGenre] = useState("");
  const [year, setYear] = useState("");
  const [minRating, setMinRating] = useState("");
  const [sortOrder, setSortOrder] = useState("");
  const [results, setResults] = useState([]);
  const [genres, setGenres] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const fetchGenres = async () => {
      try {
        const response = await axios.get("http://localhost:8080/api/genres");
        const genreList = response.data.content || response.data;
        setGenres(Array.isArray(genreList) ? genreList : []);
      } catch (err) {
        console.error("Failed to fetch genres:", err);
        setGenres([]);
      }
    };

    fetchGenres();
  }, []);

  const fetchPoster = async (title, year) => {
    try {
      const response = await axios.get(
        `https://www.omdbapi.com/?apikey=${OMDB_API_KEY}&t=${encodeURIComponent(
          title
        )}&y=${year}`
      );
      if (response.data && response.data.Poster && response.data.Poster !== "N/A") {
        return response.data.Poster;
      }
    } catch (err) {
      console.error(`Failed to fetch poster for ${title}`, err);
    }
    return null;
  };

  const handleSearch = async () => {
    let url = "http://localhost:8080/api/movies";

    if (title) {
      url += `/filter/title?title=${title}`;
    } else if (genre && year) {
      url += `/filter/genre-year?genre=${genre}&year=${year}`;
    } else if (genre && minRating) {
      url += `/filter/genre-rating?genre=${genre}&minRating=${minRating}`;
    } else if (genre) {
      url += `/filter/genre/${genre}`;
    } else if (year) {
      url += `/filter/year/${year}`;
    } else if (minRating) {
      url += `/filter/rating-above/${minRating}`;
    } else {
      alert("Please enter a search term or filter.");
      return;
    }

    setLoading(true);
    try {
      const response = await axios.get(url);
      let movies = response.data.content || response.data;

      if (sortOrder === "high") {
        movies = movies.sort((a, b) => b.rating - a.rating);
      } else if (sortOrder === "low") {
        movies = movies.sort((a, b) => a.rating - b.rating);
      }

      // Fetch OMDb posters in parallel
      const moviesWithPosters = await Promise.all(
        movies.map(async (movie) => {
          const poster = await fetchPoster(movie.title, movie.year);
          return { ...movie, poster };
        })
      );

      setResults(moviesWithPosters);
    } catch (err) {
      console.error("Search failed:", err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <Layout>
      <div style={cardStyle}>
        <h1 style={{ marginBottom: "1rem" }}> Movie Search</h1>

        <div style={filterGroupStyle}>
          <input
            type="text"
            placeholder="Title"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            style={inputStyle}
          />

          <select
            value={genre}
            onChange={(e) => setGenre(e.target.value)}
            style={inputStyle}
          >
            <option value="">All Genres</option>
            {genres.map((g) => (
              <option key={g.id} value={g.name}>
                {g.name}
              </option>
            ))}
          </select>

          <input
            type="number"
            placeholder="Year"
            value={year}
            onChange={(e) => setYear(e.target.value)}
            style={inputStyle}
          />

          <input
            type="number"
            placeholder="Min Rating"
            step="0.1"
            value={minRating}
            onChange={(e) => setMinRating(e.target.value)}
            style={inputStyle}
          />

          <select
            value={sortOrder}
            onChange={(e) => setSortOrder(e.target.value)}
            style={inputStyle}
          >
            <option value="">Sort by Rating</option>
            <option value="high">High → Low</option>
            <option value="low">Low → High</option>
          </select>

          <button onClick={handleSearch} style={buttonStyle}>
             Search
          </button>
        </div>
      </div>

      <div style={cardStyle}>
        <h2 style={sectionTitleStyle}>Results</h2>
        {loading ? (
          <p>Loading...</p>
        ) : results.length === 0 ? (
          <p>No results found.</p>
        ) : (
          <ul style={listStyle}>
            {results.map((movie) => (
              <li key={movie.tconst} style={movieCardStyle}>
                <Link to={`/movies/${movie.tconst}`} style={movieLinkStyle}>
                  <div style={{ display: "flex", alignItems: "center", gap: "1rem" }}>
                    <img
                      src={movie.poster || "https://via.placeholder.com/80x120?text=No+Image"}
                      alt={movie.title}
                      style={{
                        width: "80px",
                        height: "120px",
                        objectFit: "cover",
                        borderRadius: "8px",
                        boxShadow: "0 1px 6px rgba(0,0,0,0.1)"
                      }}
                    />
                    <div>
                      {movie.title} ({movie.year}) —  {movie.rating}
                    </div>
                  </div>
                </Link>
              </li>
            ))}
          </ul>
        )}
      </div>
    </Layout>
  );
};

export default MovieSearch;
