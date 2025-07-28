// src/pages/DirectorList.jsx
import { useEffect, useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import Layout from "../components/Layout";

const DirectorList = () => {
  const [directors, setDirectors] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [loading, setLoading] = useState(false);

  const fetchDirectors = async (pageNum = 0) => {
    setLoading(true);
    try {
      const response = await axios.get("http://localhost:8080/api/directors", {
        params: {
          page: pageNum,
          size: 10,
        },
      });
      setDirectors(response.data.content || []);
      setTotalPages(response.data.totalPages || 1);
      setPage(pageNum);
    } catch (err) {
      console.error("Failed to fetch directors:", err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchDirectors();
  }, []);

  const handlePrev = () => {
    if (page > 0) fetchDirectors(page - 1);
  };

  const handleNext = () => {
    if (page + 1 < totalPages) fetchDirectors(page + 1);
  };

  return (
    <Layout>
      <div style={containerStyle}>
        <h2 style={headingStyle}>🎬 Director List</h2>

        {loading ? (
          <p>Loading...</p>
        ) : directors.length === 0 ? (
          <p>No directors found.</p>
        ) : (
          <ul style={listStyle}>
            {directors.map((director) => (
              <li key={director.id} style={itemStyle}>
                <Link to={`/directors/${director.id}`} style={linkStyle}>
                  {director.name}
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

export default DirectorList;
