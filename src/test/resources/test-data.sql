INSERT INTO address (id, line_one, line_two, state, postal_code, country) VALUES (100,'123 Main St', 'Apt 101', 'StateName', '12345', 'UK');
INSERT INTO address (id,line_one, line_two, state, postal_code, country) VALUES (200,'456 Elm St', 'Suite 202', 'AnotherState', '23456', 'US');
INSERT INTO address (id, line_one, line_two, state, postal_code, country) VALUES (300, '123 Oak Street', 'Apt. 1', 'SomeState', '12345', 'UK');
INSERT INTO address (id, line_one, line_two, state, postal_code, country) VALUES (400, '789 Maple Avenue', '', 'GreenState', '54321', 'UK');
INSERT INTO address (id, line_one, line_two, state, postal_code, country) VALUES (500, '456 Pine Road', 'Apt. 2B', 'BlueState', '23456', 'US');
INSERT INTO address (id, line_one, line_two, state, postal_code, country) VALUES (600, '321 Birch Lane', 'Suite 5', 'RedState', '34567', 'UK');
INSERT INTO address (id, line_one, line_two, state, postal_code, country) VALUES (700, '654 Cedar Blvd', 'Unit 6', 'YellowState', '45678', 'US');

INSERT INTO author (id, author_Name) VALUES (100,'J.K. Rowling');
INSERT INTO author (id, author_Name) VALUES (200,'George Orwell');
INSERT INTO author (id, author_name) VALUES (300, 'J.R.R. Tolkien');

INSERT INTO book (id,title, author_id, publisher, genre) VALUES (100,'Harry Potter and the Philosopher''s Stone',100, 'Bloomsbury', 'Fantasy');
INSERT INTO book (id,title, author_id, publisher, genre) VALUES (200,'1984',200, 'Secker & Warburg', 'Dystopian');
INSERT INTO book (id,title, author_id, publisher, genre) VALUES (300,'Harry Potter and the Chamber of Secrets',100, 'Bloomsbury', 'Fantasy');
INSERT INTO book (id,title, author_id, publisher, genre) VALUES (400,'Harry Potter and the Prisoner of Azkaban',100, 'Bloomsbury', 'Fantasy');
INSERT INTO book (id, title, author_id, publisher, genre) VALUES (500, 'Harry Potter and the Goblet of Fire', 100, 'Bloomsbury', 'Fantasy');
INSERT INTO book (id, title, author_id, publisher, genre) VALUES (600, 'The Hobbit', 300, 'George Allen & Unwin', 'Fantasy');

INSERT INTO movie (id, title, director, screen_Writer, release_date, genre, rating) VALUES (500, 'Inception','Christopher Nolan', 'Christopher Nolan', '2010-07-16', 'Sci-Fi', 9);
INSERT INTO movie (id, title, director, screen_Writer, release_date, genre, rating) VALUES (600, 'The Great Gatsby','Baz Luhrmann', 'Baz Luhrmann', '2013-05-10', 'Drama', 7);
INSERT INTO movie (id, title, director, screen_writer, release_date, genre, rating) VALUES (700, 'Shutter Island','Martin Scorsese', 'Laeta Kalogridis', '2010-02-19', 'Thriller', 8);

INSERT INTO periodical (id, title, publication_date) VALUES (800,'Times Magazine', '2023-01-01');

INSERT INTO lead_actor (id,name, movie_id) VALUES (100,'Leonardo DiCaprio', 500);
INSERT INTO lead_actor (id,name, movie_id) VALUES (200,'Tobey Maguire', 600);
INSERT INTO lead_actor (id,name, movie_id) VALUES (300,'Leonardo DiCaprio', 600);
INSERT INTO lead_actor (id,name, movie_id) VALUES (400,'Leonardo DiCaprio', 700);

INSERT INTO librarian (id,name, address_id, phone_number, email) VALUES (100,'Alice Smith', 100, '555-0100', 'alice.smith@library.com');

INSERT INTO reader (id,name, address_id, phone_number, email) VALUES (100,'Bob Johnson', 200, '555-0200', 'bob.johnson@email.com');
INSERT INTO reader (id, name, address_id, phone_number, email) VALUES (200, 'Alice Smith', 300, '555-0300', 'alice.smith@email.com');
INSERT INTO reader (id, name, address_id, phone_number, email) VALUES (300, 'John Doe', 400, '555-0400', 'john.doe@email.com');
INSERT INTO reader (id, name, address_id, phone_number, email) VALUES (400, 'Sarah Connor', 500, '555-0500', 'sarah.connor@email.com');
INSERT INTO reader (id, name, address_id, phone_number, email) VALUES (500, 'Tom Marvolo', 600, '555-0600', 'tom.marvolo@email.com');
INSERT INTO reader (id, name, address_id, phone_number, email) VALUES (600, 'Jane Eyre', 700, '555-0700', 'jane.eyre@email.com');

INSERT INTO borrowed_book (id, book_id, reader_id, borrow_date, return_date) VALUES (100, 100, 100, '2023-01-01', NULL);
INSERT INTO borrowed_book (id, book_id, reader_id, borrow_date, return_date) VALUES (200, 200, 200, '2023-02-01', NULL);
INSERT INTO borrowed_book (id, book_id, reader_id, borrow_date, return_date) VALUES (300, 300, 300, '2023-03-01', '2023-04-01');
INSERT INTO borrowed_book (id, book_id, reader_id, borrow_date, return_date) VALUES (400, 400, 400, '2023-01-15', NULL);
INSERT INTO borrowed_book (id, book_id, reader_id, borrow_date, return_date) VALUES (500, 500, 500, '2023-02-15', '2023-03-15');