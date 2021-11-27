CREATE TABLE if not exists item (
  id SERIAL PRIMARY KEY,
  description TEXT NOT NULL UNIQUE,
  created timestamp NOT NULL,
  done boolean NOT NULL
);