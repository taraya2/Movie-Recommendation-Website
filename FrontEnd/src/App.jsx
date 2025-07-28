import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './pages/Home';
import MovieList from './pages/MovieList';
import MovieDetails from './pages/MovieDetails';
import MovieSearch from './pages/MovieSearch';
import ActorList from './pages/ActorList';
import ActorDetails from './pages/ActorDetails';
import DirectorList from './pages/DirectorList';
import DirectorDetails from './pages/DirectorDetails';
import GenreList from './pages/GenreList';
import GenreDetails from './pages/GenreDetails';
import Recommendations from './pages/Recommendations';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/movies" element={<MovieList />} />
        <Route path="/movies/:tconst" element={<MovieDetails />} />
        <Route path="/movies/search" element={<MovieSearch />} />

        <Route path="/actors" element={<ActorList />} />
        <Route path="/actors/:id" element={<ActorDetails />} />
        <Route path="/actors/:id/movies" element={<MovieList />} />

        <Route path="/directors" element={<DirectorList />} />
        <Route path="/directors/:id" element={<DirectorDetails />} />
        <Route path="/directors/:id/movies" element={<MovieList />} />

        <Route path="/genres" element={<GenreList />} />
        <Route path="/genres/:id" element={<GenreDetails />} />
        <Route path="/genres/:id/movies" element={<MovieList />} />

        <Route path="/recommend/:tconst" element={<Recommendations />} />
      </Routes>
    </Router>
  );
}

export default App;
