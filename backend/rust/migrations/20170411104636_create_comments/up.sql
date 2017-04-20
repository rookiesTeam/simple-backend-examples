-- Your SQL goes here
CREATE TABLE comments (
  id SERIAL PRIMARY KEY,
  username VARCHAR NOT NULL,
  content TEXT NOT NULL
)
