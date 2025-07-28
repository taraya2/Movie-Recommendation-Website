import { Link } from "react-router-dom";

const Layout = ({ children }) => {
  return (
    <div style={{ width: "100vw", boxSizing: "border-box" }}>
      <nav style={navStyle}>
        <Link to="/" style={linkStyle}>Home</Link>
        <Link to="/movies/search" style={linkStyle}>Search</Link>
        <Link to="/actors" style={linkStyle}>Actors</Link>
        <Link to="/directors" style={linkStyle}>Directors</Link>
        <Link to="/genres" style={linkStyle}>Genres</Link>
      </nav>
      <main style={mainStyle}>
        {children}
      </main>
    </div>
  );
};

const navStyle = {
  backgroundColor: "#222",
  padding: "1rem",
  display: "flex",
  justifyContent: "center",
  gap: "2rem",
};

const linkStyle = {
  color: "#fff",
  textDecoration: "none",
  fontWeight: "bold",
};

const mainStyle = {
  display: "flex",
  flexDirection: "column",
  alignItems: "center", // Center horizontally
  justifyContent: "flex-start",
  padding: "2rem",
  backgroundColor: "#f4f4f4",
  minHeight: "100vh",
  width: "100vw",
  boxSizing: "border-box",
};

export default Layout;
