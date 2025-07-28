import { useParams } from 'react-router-dom';

export default function Recommendations() {
  const { tconst } = useParams();

  return (
    <div>
      <h2>Recommended Movies</h2>
      <p>Base Movie ID: {tconst}</p>
    </div>
  );
}