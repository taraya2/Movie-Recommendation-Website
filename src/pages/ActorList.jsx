// src/pages/ActorList.jsx
import { useEffect, useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import Layout from "../components/Layout";

const ActorList = () => {
  const [actors, setActors] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [loading, setLoading] = useState(false);

  const fetchActors = async (pageNum = 0) => {
    setLoading(true);
    try {
      const response = await axios.get("http://localhost:8080/api/actors", {
        params: {
          page: pageNum,
          size: 10,
        },
      });
      setActors(response.data.content || []);
      setTotalPages(response.data.totalPages || 1);
      setPage(pageNum);
    } catch (err) {
      console.error("Failed to fetch actors:", err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchActors();
  }, []);

  const handlePrev = () => {
    if (page > 0) fetchActors(page - 1);
  };

  const handleNext = () => {
    if (page + 1 < totalPages) fetchActors(page + 1);
  };

  return (
    <Layout>
      <div style={containerStyle}>
        <h2 style={headingStyle}>🎭 Actor List</h2>

        {loading ? (
          <p>Loading...</p>
        ) : actors.length === 0 ? (
          <p>No actors found.</p>
        ) : (
          <ul style={listStyle}>
            {actors.map((actor) => (
              <li key={actor.id} style={itemStyle}>
                <Link to={`/actors/${actor.id}`} style={linkStyle}>
                  {actor.name}
                </Link>
              </li>
            ))}
          </ul>
        )}

        <div style={paginationStyle}>
          <button onClick={handlePrev} disabled={page === 0} style={buttonStyle}>⬅ Prev</button>
          <span><strong>Page {page + 1} of {totalPages}</strong></span>
          <button onClick={handleNext} disabled={page + 1 >= totalPages} style={buttonStyle}>Next ➡</button>
        </div>
      </div>
    </Layout>
  );
};

// Styles
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

const paginationStyle = {
  display: "flex",
  justifyContent: "space-between",
  alignItems: "center",
  marginTop: "1rem",
};

const buttonStyle = {
  padding: "0.5rem 1rem",
  borderRadius: "6px",
  border: "none",
  backgroundColor: "#007bff",
  color: "white",
  fontWeight: "bold",
  cursor: "pointer",
};

export default ActorList;
