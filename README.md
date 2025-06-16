📊 Data Engineering: IMDb TSV → MySQL
Used IMDb’s official weekly-updated TSVs from IMDb Datasets
Wrote a Java ETL script to:
Join title.basics, title.ratings, title.principals, name.basics
Filter for movies released between 2000–2023
Extract genres, cast, directors, ratings
Export to clean, normalized movies.csv
Imported the data using Spring JPA bulk inserts into MySQL
