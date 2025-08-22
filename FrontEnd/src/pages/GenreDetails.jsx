import { useParams, Link } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";
import Layout from "../components/Layout";

const GenreDetails = () => {
  const { id } = useParams();
  const [genreName, setGenreName] = useState("");
  const [allMovies, setAllMovies] = useState([]);
  const [filteredMovies, setFilteredMovies] = useState([]);
  const [posters, setPosters] = useState({});
  const [minYear, setMinYear] = useState("");
  const [maxYear, setMaxYear] = useState("");
  const [page, setPage] = useState(0);
  const [loading, setLoading] = useState(true);
  const pageSize = 15;

  // Fetch genre name
  useEffect(() => {
    const fetchGenreName = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/genres/${id}`);
        setGenreName(response.data.name);
      } catch (err) {
        console.error("Failed to fetch genre name", err);
      }
    };
    fetchGenreName();
  }, [id]);

  // Fetch all movies for genre (up to 500 or adjust if needed)
  useEffect(() => {
    const fetchAllMovies = async () => {
      setLoading(true);
      try {
        const response = await axios.get(`http://localhost:8080/api/genres/${id}/movies`, {
          params: { page: 0, size: 500 },
        });
        const movies = response.data.content;
        setAllMovies(movies);
        setFilteredMovies(movies);
      } catch (err) {
        console.error("Failed to fetch genre movies", err);
      } finally {
        setLoading(false);
      }
    };
    fetchAllMovies();
  }, [id]);

  // Filter logic
  const handleFilter = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/api/genres/${id}/movies`, {
        params: {
          minYear: minYear || undefined,
          maxYear: maxYear || undefined,
          page: 0,
          size: 500,
        },
      });
  
      const filtered = response.data.content;
      setFilteredMovies(filtered);
      setPage(0); // reset to first page
    } catch (err) {
      console.error("Failed to fetch filtered movies", err);
    }
  };
  

  // Pagination
  const paginated = filteredMovies.slice(page * pageSize, (page + 1) * pageSize);

  const handlePrev = () => {
    if (page > 0) setPage(page - 1);
  };

  const handleNext = () => {
    if ((page + 1) * pageSize < filteredMovies.length) setPage(page + 1);
  };

  // Fetch posters for currently visible movies
  useEffect(() => {
    const fetchPosters = async () => {
      const currentPageMovies = paginated;
      const newPosters = { ...posters };

      await Promise.all(
        currentPageMovies.map(async (movie) => {
          if (!newPosters[movie.tconst]) {
            try {
              const res = await axios.get(
                `https://www.omdbapi.com/?i=${movie.tconst}&apikey=APIKEY`
              );
              if (res.data.Poster && res.data.Poster !== "N/A") {
                newPosters[movie.tconst] = res.data.Poster;
              }
            } catch (err) {
              console.warn(`Poster fetch failed for ${movie.tconst}`);
            }
          }
        })
      );

      setPosters(newPosters);
    };

    if (paginated.length > 0) {
      fetchPosters();
    }
  }, [paginated]);

  return (
    <Layout>
      <h2 style={{ color: "#f5f5f5" }}>Genre: {genreName}</h2>

      {/* Filter Controls */}
      <div
        style={{
          display: "flex",
          gap: "10px",
          alignItems: "center",
          marginBottom: "1.5rem",
          flexWrap: "wrap",
        }}
      >
        <input
          type="number"
          placeholder="Min Year"
          value={minYear}
          onChange={(e) => setMinYear(e.target.value)}
          style={{
            width: "120px",
            padding: "10px",
            fontSize: "1rem",
            borderRadius: "8px",
            border: "1px solid #555",
            backgroundColor: "#222",
            color: "#fff",
          }}
        />
        <input
          type="number"
          placeholder="Max Year"
          value={maxYear}
          onChange={(e) => setMaxYear(e.target.value)}
          style={{
            width: "120px",
            padding: "10px",
            fontSize: "1rem",
            borderRadius: "8px",
            border: "1px solid #555",
            backgroundColor: "#222",
            color: "#fff",
          }}
        />
        <button
          onClick={handleFilter}
          style={{
            padding: "10px 16px",
            backgroundColor: "#0077cc",
            color: "white",
            border: "none",
            borderRadius: "5px",
            cursor: "pointer",
            fontSize: "1rem",
          }}
        >
          Filter
        </button>
      </div>

      {loading ? (
        <p style={{ color: "#ccc" }}>Loading movies...</p>
      ) : paginated.length === 0 ? (
        <p style={{ color: "#ccc" }}>No movies found for this filter.</p>
      ) : (
        <>
          <div
            style={{
              display: "grid",
              gridTemplateColumns: "repeat(5, 1fr)",
              gap: "1.5rem",
              marginBottom: "2rem",
            }}
          >
            {paginated.map((movie) => (
              <Link
                key={movie.tconst}
                to={`/movies/${movie.tconst}`}
                style={{
                  textDecoration: "none",
                  color: "#ddd",
                  backgroundColor: "#1c1c1c",
                  borderRadius: "10px",
                  overflow: "hidden",
                  boxShadow: "0 0 10px rgba(0,0,0,0.4)",
                  display: "flex",
                  flexDirection: "column",
                }}
              >
                <div
                  style={{
                    width: "100%",
                    height: "240px",
                    backgroundColor: "#333",
                    display: "flex",
                    alignItems: "center",
                    justifyContent: "center",
                  }}
                >
                  {posters[movie.tconst] ? (
                    <img
                      src={posters[movie.tconst]}
                      alt={`${movie.title} poster`}
                      style={{
                        width: "100%",
                        height: "100%",
                        objectFit: "cover",
                      }}
                    />
                  ) : (
                    <div style={{ color: "#888", fontSize: "0.9rem" }}>No Image</div>
                  )}
                </div>
                <div style={{ padding: "0.5rem", textAlign: "center" }}>
                  <div style={{ color: "#fff", fontSize: "1rem" }}>{movie.title}</div>
                  <div style={{ color: "#aaa", fontSize: "0.9rem" }}>({movie.year})</div>
                </div>
              </Link>
            ))}
          </div>

          {/* Pagination */}
          <div style={{ textAlign: "center", marginBottom: "2rem" }}>
            <button
              onClick={handlePrev}
              disabled={page === 0}
              style={{
                marginRight: "10px",
                padding: "8px 16px",
                backgroundColor: "#0077cc",
                color: "white",
                border: "none",
                borderRadius: "5px",
                cursor: page === 0 ? "not-allowed" : "pointer",
                opacity: page === 0 ? 0.5 : 1,
              }}
            >
              Previous
            </button>
            <span style={{ color: "#ddd", margin: "0 12px" }}>
              Page {page + 1} of {Math.ceil(filteredMovies.length / pageSize)}
            </span>
            <button
              onClick={handleNext}
              disabled={(page + 1) * pageSize >= filteredMovies.length}
              style={{
                marginLeft: "10px",
                padding: "8px 16px",
                backgroundColor: "#0077cc",
                color: "white",
                border: "none",
                borderRadius: "5px",
                cursor: (page + 1) * pageSize >= filteredMovies.length ? "not-allowed" : "pointer",
                opacity: (page + 1) * pageSize >= filteredMovies.length ? 0.5 : 1,
              }}
            >
              Next
            </button>
          </div>
        </>
      )}
    </Layout>
  );
};

export default GenreDetails;
