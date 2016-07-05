CREATE TABLE contact (
	id SERIAL PRIMARY KEY,
	contact_type VARCHAR(255),
	value VARCHAR(255),
	person_id INT UNIQUE NOT NULL,
    CONSTRAINT person_fkey FOREIGN KEY (person_id)
    REFERENCES person (id)
);
