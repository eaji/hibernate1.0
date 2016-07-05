
--create table persons_role (
--   person_id INT UNIQUE NOT NULL,
--   role_id INT UNIQUE NOT NULL,
--   PRIMARY KEY (person_id, role_id)
--);





CREATE TABLE role (
id SERIAL PRIMARY KEY,
role VARCHAR(255)
);



create table person_role (
person_id INT NOT NULL,
role_id INT NOT NULL,
PRIMARY KEY (person_id, role_id),
foreign key (person_id) references PERSON(id),
foreign key (role_id) references ROLE(id)
);







INSERT INTO role (id, role) VALUES (1, 'Software Developer');

INSERT INTO role (id, role) VALUES (2, 'Quality Assurance');

INSERT INTO role (id, role) VALUES (3, 'Team Lead');

INSERT INTO role (id, role) VALUES (4, 'Project Manager');

























INSERT INTO person_role (person_id, role_id) VALUES (1, 1);



INSERT INTO address (id, street_number, barangay, city, zip_code) VALUES (1, 111, 'wanwan', 'wan', 1111);

INSERT INTO person (id, last_name, first_name, middle_name, birthdate, gwa, date_hired,employed) VALUES (1, 'james', 'le', 'bron', '1999/09/09', 4.0, '2009/09/09', true);

INSERT INTO contact (id, contact_type, value, person_id) VALUES (1, Mobile, 11111111111, 1);

























//
INSERT INTO address (id, street_number, barangay, city, zip_code) VALUES (1, 111, 'wanwan', 'wan', 1111);

INSERT INTO person (id, last_name, first_name, middle_name, birthdate, address_id, gwa, date_hired,employed) VALUES (1, 'james', 'le', 'bron', '1999/09/09', 1, 4.0, '2009/09/09', true);

INSERT INTO contact (id, contact_type, value, person_id) VALUES (1, 1, 11111111111, 1);

