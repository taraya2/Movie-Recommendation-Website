// src/pages/GenreList.jsx
import { useEffect, useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import Layout from "../components/Layout";

const GenreList = () => {
  const [genres, setGenres] = useState([]);
  const [loading, setLoading] = useState(false);

  const fetchGenres = async () => {
    setLoading(true);
    try {
      const response = await axios.get("http://localhost:8080/api/genres");
      setGenres(response.data.content || []);
    } catch (err) {
      console.error("Failed to fetch genres:", err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchGenres();
  }, []);

  return (
    <Layout>
      <div style={containerStyle}>
        <h2 style={headingStyle}> Genre List</h2>

        {loading ? (
          <p>Loading...</p>
        ) : genres.length === 0 ? (
          <p>No genres found.</p>
        ) : (
          <ul style={listStyle}>
            {genres.map((genre) => (
              <li key={genre.id} style={itemStyle}>
                <Link to={`/genres/${genre.id}`} style={linkStyle}>
                  {genre.name}
                </Link>
              </li>
            ))}
          </ul>
        )}
      </div>
    </Layout>
  );
};

// Reused Styles
const containerStyle = {
  width: "100%",
  backgroundColor: "#fff",
  padding: "2rem",
  borderRadius: "12px",
  boxShadow: "0 2px 8px rgba(0,0,0,0.08)",
  boxSizing: "border-box",
};

const headingStyle = {
  marginBottom: "1rem",
  color: "#333",
};

const listStyle = {
  listStyle: "none",
  padding: 0,
};

const itemStyle = {
  padding: "1rem 0",
  borderBottom: "1px solid #eee",
};

const linkStyle = {
  textDecoration: "none",
  color: "#007bff",
  fontSize: "1.1rem",
  fontWeight: "bold",
};

export default GenreList;
