CREATE TABLE if not exists item (
  id SERIAL PRIMARY KEY,
  description TEXT NOT NULL UNIQUE,
  created timestamp NOT NULL,
  done boolean NOT NULL,
  user_id int references users(id);
);

CREATE TABLE if not exists users (
  id SERIAL PRIMARY KEY,
  name TEXT NOT NULL
);