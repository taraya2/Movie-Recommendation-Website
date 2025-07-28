function MovieCard({ movie }) {
    return (
      <div style={{
        border: '1px solid #ccc',
        padding: '1rem',
        borderRadius: '8px',
        width: '200px',
        background: '#f9f9f9'
      }}>
        <h3>{movie.title}</h3>
        <p><strong>Year:</strong> {movie.year}</p>
        <p><strong>Rating:</strong> {movie.rating}</p>
      </div>
    );
  }
  
  export default MovieCard;
  