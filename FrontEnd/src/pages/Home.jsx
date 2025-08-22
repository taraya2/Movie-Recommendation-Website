import { useEffect, useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import Layout from "../components/Layout";

const OMDB_API_KEY = "APIKEY";

const Home = () => {
  const [movies, setMovies] = useState([]);
  const [loading, setLoading] = useState(false);

  const fetchMovies = async () => {
    setLoading(true);
    try {
      const res = await axios.get("http://localhost:8080/api/movies");
      let fetched = res.data.content || res.data || [];

      // Sort by year (descending) and limit to first 100
      fetched.sort((a, b) => b.year - a.year);
      const sample = fetched.slice(0, 100);

      const moviesWithPosters = [];
      let i = 0;

      while (moviesWithPosters.length < 20 && i < sample.length) {
        const movie = sample[i];
        i++;
      
        try {
          const res = await axios.get(
            `https://www.omdbapi.com/?i=${movie.tconst}&apikey=${OMDB_API_KEY}`
          );
      
          const data = res.data;
      
          // Debugging: log responses with missing or invalid poster
          if (data.Response !== "True" || !data.Poster || data.Poster === "N/A") {
            console.log(`Skipped ${movie.tconst}:`, data);
            continue;
          }
      
          // Add only valid posters
          moviesWithPosters.push({
            ...movie,
            poster: data.Poster,
          });
        } catch (err) {
          console.warn(`Error fetching poster for ${movie.tconst}`, err);
          continue;
        }
      }
      

      setMovies(moviesWithPosters);
    } catch (err) {
      console.error("Failed to fetch movies:", err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchMovies();
  }, []);

  return (
    <Layout>
      <div style={containerStyle}>
        <h2 style={headingStyle}></h2>

        {loading ? (
          <p style={{ color: "#ccc" }}>Loading movies...</p>
        ) : movies.length === 0 ? (
          <p style={{ color: "#ccc" }}>No movies with posters found.</p>
        ) : (
          <div style={gridContainerStyle}>
            {movies.map((movie) => (
              <Link
                to={`/movies/${movie.tconst}`}
                key={movie.tconst}
                style={movieCardStyle}
              >
                <img
                  src={movie.poster}
                  alt={`${movie.title} poster`}
                  style={posterStyle}
                />
                <div style={movieInfoStyle}>
                  <h3 style={movieTitleStyle}>{movie.title}</h3>
                  <p style={movieYearStyle}>{movie.year}</p>
                  <p style={movieRatingStyle}>{movie.rating}</p>
                </div>
              </Link>
            ))}
          </div>
        )}
      </div>
    </Layout>
  );
};

// --- Styles ---
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

export default Home;
