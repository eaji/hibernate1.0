CREATE TABLE person (
	id SERIAL PRIMARY KEY,
	last_name VARCHAR(255),
	first_name VARCHAR(255),
	middle_name VARCHAR(255),
	birthdate date,
	gwa real,
	date_hired date,
	employed boolean
);

--CREATE TYPE mood AS ENUM ('sad', 'ok', 'happy');
--CREATE TABLE person (
--   name text,
--    current_mood mood
--);
--INSERT INTO person VALUES ('Moe', 'happy');
--SELECT * FROM person WHERE current_mood = 'happy';
