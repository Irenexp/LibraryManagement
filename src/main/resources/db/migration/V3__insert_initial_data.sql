INSERT INTO address (line_one, line_two, state, postal_code, country) VALUES ('123 Main St', 'Apt 101', 'StateName', '12345', 'CountryName');

INSERT INTO author (authorName) VALUES ('J.K. Rowling');
INSERT INTO author (authorName) VALUES ('George Orwell');

INSERT INTO lendable_material (id, title, type) VALUES (1, 'Harry Potter and the Philosopher''s Stone', 'Book');
INSERT INTO lendable_material (id, title, type) VALUES (2, '1984', 'Book');
INSERT INTO lendable_material (id, title, type) VALUES (3, 'Inception', 'Movie');
INSERT INTO lendable_material (id, title, type) VALUES (4, 'Times Magazine', 'Periodical');
INSERT INTO lendable_material (id, title, type) VALUES (5, 'Harry Potter and the Chamber of Secrets', 'Book');
INSERT INTO lendable_material (id, title, type) VALUES (6, 'Harry Potter and the Prisoner of Azkaban', 'Book');
INSERT INTO lendable_material (id, title, type) VALUES (7, 'The Great Gatsby', 'Movie');
INSERT INTO lendable_material (id, title, type) VALUES (8, 'Shutter Island', 'Movie');

INSERT INTO book (id, author_id, publisher, genre) VALUES (1, 1, 'Bloomsbury', 'Fantasy');
INSERT INTO book (id, author_id, publisher, genre) VALUES (2, 2, 'Secker & Warburg', 'Dystopian');
INSERT INTO book (id, author_id, publisher, genre) VALUES (5, 1, 'Bloomsbury', 'Fantasy');
INSERT INTO book (id, author_id, publisher, genre) VALUES (6, 1, 'Bloomsbury', 'Fantasy');

INSERT INTO movie (id, director, screenWriter, release_date, genre, rating) VALUES (3, 'Christopher Nolan', 'Christopher Nolan', '2010-07-16', 'Sci-Fi', 9);
INSERT INTO movie (id, director, screenWriter, release_date, genre, rating) VALUES (7, 'Baz Luhrmann', 'Baz Luhrmann', '2013-05-10', 'Drama', 7);
INSERT INTO movie (id, director, screenwriter, release_date, genre, rating) VALUES (8, 'Martin Scorsese', 'Laeta Kalogridis', '2010-02-19', 'Thriller', 8);

INSERT INTO periodical (id, publication_date) VALUES (4, '2023-01-01');

INSERT INTO lead_actor (name, movie_id) VALUES ('Leonardo DiCaprio', 3);
INSERT INTO lead_actor (name, movie_id) VALUES ('Tobey Maguire', 7);
INSERT INTO lead_actor (name, movie_id) VALUES ('Leonardo DiCaprio', 7);
INSERT INTO lead_actor (name, movie_id) VALUES ('Leonardo DiCaprio', 8);

INSERT INTO librarian (name, address_id, phone_number, email) VALUES ('Alice Smith', 1, '555-0100', 'alice.smith@library.com');

INSERT INTO reader (name, address_id, phone_number, email) VALUES ('Bob Johnson', 2, '555-0200', 'bob.johnson@email.com');
