import { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import axios from "axios";
import Layout from "../components/Layout";

const MovieDetails = () => {
  const { tconst } = useParams();
  const [movie, setMovie] = useState(null);
  const [recommendations, setRecommendations] = useState([]);
  const [posterUrl, setPosterUrl] = useState(null);
  const [recPosters, setRecPosters] = useState({});
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchMovieDetails = async () => {
      try {
        const [movieRes, recRes] = await Promise.all([
          axios.get(`http://localhost:8080/api/movies/${tconst}`),
          axios.get(`http://localhost:8080/api/recommend/${tconst}?limit=10`),
        ]);
        setMovie(movieRes.data);
        setRecommendations(recRes.data);
      } catch (err) {
        console.error("Failed to load movie or recommendations", err);
      } finally {
        setLoading(false);
      }
    };
    fetchMovieDetails();
  }, [tconst]);

  useEffect(() => {
    const fetchPoster = async () => {
      try {
        const response = await axios.get(
          `https://www.omdbapi.com/?i=${tconst}&apikey=3170ca1b`
        );
        if (response.data.Poster && response.data.Poster !== "N/A") {
          setPosterUrl(response.data.Poster);
        }
      } catch (err) {
        console.error("Failed to fetch poster from OMDb", err);
      }
    };
    fetchPoster();
  }, [tconst]);

  useEffect(() => {
    const fetchRecommendationPosters = async () => {
      const postersMap = {};
      await Promise.all(
        recommendations.map(async (rec) => {
          try {
            const res = await axios.get(
              `https://www.omdbapi.com/?i=${rec.tconst}&apikey=3170ca1b`
            );
            if (res.data.Poster && res.data.Poster !== "N/A") {
              postersMap[rec.tconst] = res.data.Poster;
            }
          } catch (e) {
            console.warn(`Poster fetch failed for ${rec.tconst}`);
          }
        })
      );
      setRecPosters(postersMap);
    };

    if (recommendations.length > 0) {
      fetchRecommendationPosters();
    }
  }, [recommendations]);

  if (loading) return <p style={{ color: "#ccc" }}>Loading movie details...</p>;
  if (!movie) return <p style={{ color: "#ccc" }}>Movie not found.</p>;

  return (
    <Layout>
      <h2 style={{ color: "#f5f5f5" }}>{movie.title}</h2>

      {posterUrl && (
        <div style={{ marginBottom: "1rem" }}>
          <img
            src={posterUrl}
            alt={`${movie.title} poster`}
            style={{ maxWidth: "300px", borderRadius: "8px", boxShadow: "0 0 12px #222" }}
          />
        </div>
      )}

      <p style={{ color: "#ccc" }}>
        <strong style={{ color: "#aaa" }}>Year:</strong> {movie.year}
      </p>
      <p style={{ color: "#ccc" }}>
        <strong style={{ color: "#aaa" }}>Rating:</strong>  {movie.rating}
      </p>
      <p style={{ color: "#ccc" }}>
        <strong style={{ color: "#aaa" }}>Votes:</strong> {movie.numVotes}
      </p>

      <p style={{ color: "#ccc" }}>
        <strong style={{ color: "#aaa" }}>Genres:</strong>{" "}
        {movie.genres?.map((g, index) => (
          <span key={g.id}>
            <Link to={`/genres/${g.id}`} style={{ color: "#66b3ff", textDecoration: "none" }}>
              {g.name}
            </Link>
            {index < movie.genres.length - 1 && ", "}
          </span>
        ))}
      </p>

      <p style={{ color: "#ccc" }}>
        <strong style={{ color: "#aaa" }}>Directors:</strong>{" "}
        {movie.directors?.map((d, index) => (
          <span key={d.id}>
            <Link to={`/directors/${d.id}`} style={{ color: "#66b3ff", textDecoration: "none" }}>
              {d.name}
            </Link>
            {index < movie.directors.length - 1 && ", "}
          </span>
        ))}
      </p>

      <p style={{ color: "#ccc" }}>
        <strong style={{ color: "#aaa" }}>Top Cast:</strong>{" "}
        {movie.topCast?.map((a, index) => (
          <span key={a.id}>
            <Link to={`/actors/${a.id}`} style={{ color: "#66b3ff", textDecoration: "none" }}>
              {a.name}
            </Link>
            {index < movie.topCast.length - 1 && ", "}
          </span>
        ))}
      </p>

      <h3 style={{ color: "#eee", marginTop: "2rem" }}>Recommended Movies</h3>
      {recommendations.length === 0 ? (
        <p style={{ color: "#ccc" }}>No recommendations found.</p>
      ) : (
        <ul style={{ listStyle: "none", padding: 0 }}>
          {recommendations.map((rec) => (
            <li key={rec.tconst} style={{ display: "flex", alignItems: "center", marginBottom: "1rem" }}>
              <img
                src={recPosters[rec.tconst] || "https://via.placeholder.com/50x75?text=No+Image"}
                alt={`${rec.title} poster`}
                style={{ width: "50px", height: "75px", objectFit: "cover", borderRadius: "4px", marginRight: "1rem", boxShadow: "0 0 4px #111" }}
              />
              <Link to={`/movies/${rec.tconst}`} style={{ color: "#66b3ff", textDecoration: "none" }}>
                <strong>{rec.title}</strong>{" "}
                <span style={{ color: "#aaa" }}>({rec.year}) —  {rec.rating}</span>
              </Link>
            </li>
          ))}
        </ul>
      )}
    </Layout>
  );
};

export default MovieDetails;
