
# Full-Stack Movie Recommendation Platform

Full-stack web application that enables users to explore, search, and receive recommendations for movies using a clean, scalable architecture. It integrates a custom movie dataset from IMDb, real-time poster fetching via OMDb API, and interactive filtering powered by a modern REST API.

---

## Key Features

- **Genre-based Filtering**: Explore movies by genre and filter by year range.
- **Movie Recommendations**: Each movie provides context-aware related movie recommendations.
- **Search Functionality**: Search and navigate by movie title, director, actor, or genre.
- **Movie Detail Pages**: Each movie has its own detailed view, including cast, genres, rating, and poster.
- **Director & Actor Browsing**: View filmographies for individual directors and actors.
- **Real-Time Posters**: Posters dynamically fetched from OMDb API based on movie title and year.
- **Responsive Frontend**: Built with React and Axios for fast and dynamic user experience.
- **Efficient Backend**: Spring Boot REST API with JPA, pagination, and custom query filtering.

---
https://github.com/user-attachments/assets/ff7585f8-c861-4ec2-ad56-44182fad8306

---




https://github.com/user-attachments/assets/b8bef803-da79-49fd-a7a1-92e637be7d4a




## Technology Stack

### Backend
- Java
- Spring Boot
- Spring Data JPA
- PostgreSQL
- OMDb API (for poster retrieval)

### Frontend
- React (Vite)
- React Router DOM
- Axios
- CSS Modules / Inline Styling

---

## Data Engineering & ETL

Used official IMDb TSV datasets to build a clean and normalized movie dataset.

- Parsed and joined: `title.basics.tsv`, `title.ratings.tsv`, `title.principals.tsv`, `name.basics.tsv`
- Filtered for:
  - Movies only
  - Release years: 2000–2023
  - Valid ratings, directors, and cast
- Extracted structured columns: genres, directors, primary cast, and ratings
- Wrote a Java-based ETL process to:
  - Normalize and transform data into a clean CSV
  - Load data into PostgreSQL using Spring Boot and JPA with batch inserts for performance
