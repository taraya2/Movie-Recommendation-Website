import { useParams, Link } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";
import Layout from "../components/Layout";

const ActorDetails = () => {
  const { id } = useParams();
  const [actor, setActor] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchActor = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/actors/${id}`);
        setActor(response.data);
      } catch (err) {
        console.error("Failed to fetch actor", err);
      } finally {
        setLoading(false);
      }
    };

    fetchActor();
  }, [id]);

  if (loading) return <p>Loading actor details...</p>;
  if (!actor) return <p>Actor not found.</p>;

  return (
    <Layout>
      <h2 style={{ color: "#222" }}>{actor.name}</h2>
      <h3 style={{ color: "#333" }}>Known For</h3>
      <ul>
        {actor.movies?.map((movie) => (
          <li key={movie.tconst}>
            <Link to={`/movies/${movie.tconst}`} style={{ color: "#0077cc" }}>
              {movie.title} ({movie.year})
            </Link>
          </li>
        ))}
      </ul>
    </Layout>
  );
};

export default ActorDetails;
