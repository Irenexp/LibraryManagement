CREATE TABLE lendable_material (
                                   id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                   title VARCHAR(255)

);

CREATE TABLE author (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        authorName VARCHAR(255)
);

CREATE TABLE book (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      author_id BIGINT,
                      publisher VARCHAR(255),
                      genre VARCHAR(255),
                      FOREIGN KEY (author_id) REFERENCES author(id),
                      FOREIGN KEY (id) REFERENCES lendable_material(id)
);

CREATE TABLE movie (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       director VARCHAR(255),
                       screenwriter VARCHAR(255),
                       release_date TIMESTAMP,
                       genre VARCHAR(255),
                       rating INTEGER,
                       FOREIGN KEY (id) REFERENCES lendable_material(id)
);

CREATE TABLE periodical (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            publication_date DATE,
                            FOREIGN KEY (id) REFERENCES lendable_material(id)
);


CREATE TABLE lead_actor (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(255),
                            movie_id BIGINT,
                            FOREIGN KEY (movie_id) REFERENCES movie(id)
);

CREATE TABLE address (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         line_one VARCHAR(255),
                         line_two VARCHAR(255),
                         state VARCHAR(100),
                         postal_code VARCHAR(20),
                         country VARCHAR(100)
);

CREATE TABLE librarian (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(255),
                           address_id BIGINT,
                           phone_number VARCHAR(20),
                           email VARCHAR(255),
                           FOREIGN KEY (address_id) REFERENCES address(id)
);

CREATE TABLE reader (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(255),
                      address_id BIGINT,
                      phone_number VARCHAR(20),
                      email VARCHAR(255),
                      FOREIGN KEY (address_id) REFERENCES address(id)
);
