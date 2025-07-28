import { useParams, Link } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";
import Layout from "../components/Layout";

const DirectorDetails = () => {
  const { id } = useParams();
  const [director, setDirector] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchDirector = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/directors/${id}`);
        setDirector(response.data);
      } catch (err) {
        console.error("Failed to fetch director", err);
      } finally {
        setLoading(false);
      }
    };

    fetchDirector();
  }, [id]);

  if (loading) return <p>Loading director details...</p>;
  if (!director) return <p>Director not found.</p>;

  return (
    <Layout>
      <h2 style={{ color: "#222" }}>{director.name}</h2>
      <h3 style={{ color: "#333" }}>Movies Directed</h3>
      <ul>
        {director.movies?.map((movie) => (
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

export default DirectorDetails;
