// src/pages/MovieList.jsx
import { useEffect, useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import Layout from "../components/Layout";

const OMDB_API_KEY = "3170ca1b"; 

const MovieList = () => {
  const [movies, setMovies] = useState([]);
  const [genres, setGenres] = useState([]);
  const [genreFilter, setGenreFilter] = useState("");
  const [sortOrder, setSortOrder] = useState("");
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [loading, setLoading] = useState(false);
  const [posters, setPosters] = useState({}); // Map tconst -> poster URL

  // Fetch genres for filter dropdown
  const fetchGenres = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/genres");
      const list = response.data.content || response.data;
      setGenres(Array.isArray(list) ? list : []);
    } catch (err) {
      console.error("Failed to fetch genres:", err);
    }
  };

  // Fetch movies for the current page/filter
  const fetchMovies = async (pageNum = 0) => {
    setLoading(true);
    try {
      let url = "http://localhost:8080/api/movies";
      const params = {
        page: pageNum,
        size: 15,
      };

      if (genreFilter) url += `/filter/genre/${genreFilter}`;

      const response = await axios.get(url, { params });
      let fetched = response.data.content || [];

      if (sortOrder === "high") {
        fetched = fetched.sort((a, b) => b.rating - a.rating);
      } else if (sortOrder === "low") {
        fetched = fetched.sort((a, b) => a.rating - b.rating);
      }

      setMovies(fetched);
      setTotalPages(response.data.totalPages || 1);
      setPage(pageNum);

      // Fetch posters for these movies
      fetchPostersForMovies(fetched);
    } catch (err) {
      console.error("Failed to fetch movies:", err);
    } finally {
      setLoading(false);
    }
  };

  // Fetch poster URLs from OMDb API for a batch of movies
  const fetchPostersForMovies = async (movies) => {
    const newPosters = { ...posters };

    await Promise.all(
      movies.map(async (movie) => {
        if (!newPosters[movie.tconst]) {
          try {
            const res = await axios.get(`https://www.omdbapi.com/?i=${movie.tconst}&apikey=${OMDB_API_KEY}`);
            if (res.data.Poster && res.data.Poster !== "N/A") {
              newPosters[movie.tconst] = res.data.Poster;
            } else {
              newPosters[movie.tconst] = null; // no poster available
            }
          } catch {
            newPosters[movie.tconst] = null;
          }
        }
      })
    );

    setPosters(newPosters);
  };

  useEffect(() => {
    fetchGenres();
    fetchMovies();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  useEffect(() => {
    fetchMovies(0); // reset to first page on filter or sort change
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [genreFilter, sortOrder]);

  const handlePrev = () => {
    if (page > 0) fetchMovies(page - 1);
  };

  const handleNext = () => {
    if (page + 1 < totalPages) fetchMovies(page + 1);
  };

  return (
    <Layout>
      <div style={containerStyle}>
        <h2 style={headingStyle}> Movie List</h2>

        <div style={filterGroupStyle}>
          <select
            value={genreFilter}
            onChange={(e) => setGenreFilter(e.target.value)}
            style={inputStyle}
          >
            <option value="">All Genres</option>
            {genres.map((g) => (
              <option key={g.id} value={g.name}>
                {g.name}
              </option>
            ))}
          </select>

          <select
            value={sortOrder}
            onChange={(e) => setSortOrder(e.target.value)}
            style={inputStyle}
          >
            <option value="">Sort by Rating</option>
            <option value="high">High → Low</option>
            <option value="low">Low → High</option>
          </select>
        </div>

        {loading ? (
          <p style={{ color: "#ccc" }}>Loading movies...</p>
        ) : movies.length === 0 ? (
          <p style={{ color: "#ccc" }}>No movies found.</p>
        ) : (
          <div style={gridContainerStyle}>
            {movies.map((movie) => (
              <Link
                to={`/movies/${movie.tconst}`}
                key={movie.tconst}
                style={movieCardStyle}
                title={`${movie.title} (${movie.year}) —  ${movie.rating}`}
              >
                <img
                  src={
                    posters[movie.tconst] ||
                    "https://via.placeholder.com/180x270?text=No+Image"
                  }
                  alt={`${movie.title} poster`}
                  style={posterStyle}
                />
                <div style={movieInfoStyle}>
                  <h3 style={movieTitleStyle}>{movie.title}</h3>
                  <p style={movieYearStyle}>{movie.year}</p>
                  <p style={movieRatingStyle}> {movie.rating}</p>
                </div>
              </Link>
            ))}
          </div>
        )}

        <div style={paginationStyle}>
          <button
            onClick={handlePrev}
            disabled={page === 0}
            style={{ ...buttonStyle, opacity: page === 0 ? 0.5 : 1 }}
          >
            ⬅ Prev
          </button>
          <span style={{ color: "#ccc" }}>
            Page {page + 1} of {totalPages}
          </span>
          <button
            onClick={handleNext}
            disabled={page + 1 >= totalPages}
            style={{ ...buttonStyle, opacity: page + 1 >= totalPages ? 0.5 : 1 }}
          >
            Next ➡
          </button>
        </div>
      </div>
    </Layout>
  );
};

// Styles
const containerStyle = {
  width: "100%",
  maxWidth: "1100px",
  backgroundColor: "#121212",
  padding: "2rem",
  borderRadius: "12px",
  boxSizing: "border-box",
  margin: "2rem auto",
  color: "#eee",
};

const headingStyle = {
  marginBottom: "1.5rem",
  color: "#eee",
  textAlign: "center",
};

const filterGroupStyle = {
  display: "flex",
  justifyContent: "center",
  gap: "1rem",
  marginBottom: "1.5rem",
  flexWrap: "wrap",
};

const inputStyle = {
  padding: "0.5rem 1rem",
  borderRadius: "6px",
  border: "1px solid #444",
  backgroundColor: "#222",
  color: "#eee",
  minWidth: "160px",
  fontSize: "1rem",
};

const gridContainerStyle = {
  display: "grid",
  gridTemplateColumns: "repeat(auto-fit, minmax(180px, 1fr))",
  gap: "1.5rem",
};

const movieCardStyle = {
  display: "flex",
  flexDirection: "column",
  textDecoration: "none",
  backgroundColor: "#1f1f1f",
  borderRadius: "8px",
  boxShadow: "0 2px 8px rgba(0,0,0,0.7)",
  overflow: "hidden",
  color: "#eee",
  cursor: "pointer",
  transition: "transform 0.15s ease-in-out",
  padding: "0.5rem",
};

const posterStyle = {
  width: "100%",
  height: "270px",
  objectFit: "cover",
  borderRadius: "6px",
  marginBottom: "0.5rem",
};

const movieInfoStyle = {
  flexGrow: 1,
  textAlign: "center",
};

const movieTitleStyle = {
  fontSize: "1.1rem",
  margin: "0 0 0.3rem 0",
  color: "#66b3ff",
};

const movieYearStyle = {
  fontSize: "0.9rem",
  margin: 0,
  color: "#bbb",
};

const movieRatingStyle = {
  fontSize: "0.9rem",
  marginTop: "0.3rem",
  color: "#ffd700",
  fontWeight: "bold",
};

const paginationStyle = {
  display: "flex",
  justifyContent: "center",
  alignItems: "center",
  gap: "1.5rem",
  marginTop: "2rem",
};

const buttonStyle = {
  padding: "0.6rem 1.2rem",
  borderRadius: "6px",
  border: "none",
  backgroundColor: "#66b3ff",
  color: "black",
  fontWeight: "bold",
  cursor: "pointer",
  transition: "background-color 0.3s",
};

export default MovieList;
