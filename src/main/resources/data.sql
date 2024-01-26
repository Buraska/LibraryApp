-- Insert roles into the Role table


-- Insert users into the Person table with encrypted passwords
INSERT INTO Person (name, date_of_birth, created_at, username, password)
VALUES ('John Doe', '1990-05-15', CURRENT_TIMESTAMP, 'john_admin', '$2a$12$e7rRuLhVMi4YuelFvoCKU.BlMuTxuW6MZnfWkRbt8G/ApphpDuC5e'); -- 123

INSERT INTO Person (name, date_of_birth, created_at, username, password)
VALUES ('Jane Smith', '1985-08-23', CURRENT_TIMESTAMP, 'jane_user', '$2a$12$e7rRuLhVMi4YuelFvoCKU.BlMuTxuW6MZnfWkRbt8G/ApphpDuC5e');

INSERT INTO Person (name, date_of_birth, created_at, username, password)
VALUES ('Robert Johnson', '1978-11-30', CURRENT_TIMESTAMP, 'robert_user', '$2a$12$e7rRuLhVMi4YuelFvoCKU.BlMuTxuW6MZnfWkRbt8G/ApphpDuC5e');

INSERT INTO Person (name, date_of_birth, created_at, username, password)
VALUES ('Alice Brown', '1995-03-10', CURRENT_TIMESTAMP, 'alice_user', '$2a$12$e7rRuLhVMi4YuelFvoCKU.BlMuTxuW6MZnfWkRbt8G/ApphpDuC5e');

INSERT INTO Person (name, date_of_birth, created_at, username, password)
VALUES ('Chris Wilson', '1980-09-18', CURRENT_TIMESTAMP, 'chris_user', '$2a$12$e7rRuLhVMi4YuelFvoCKU.BlMuTxuW6MZnfWkRbt8G/ApphpDuC5e');

-- Insert connections between persons and roles
-- John Doe is an ADMIN


INSERT INTO Role (authority) VALUES ('ROLE_ADMIN');
INSERT INTO Role (authority) VALUES ('ROLE_USER');


INSERT INTO person_role (person_id, role_id)
VALUES ((SELECT id FROM Person WHERE username = 'john_admin'), (SELECT id FROM Role WHERE authority = 'ROLE_ADMIN'));

-- Jane Smith is a USER
INSERT INTO person_role (person_id, role_id)
VALUES ((SELECT id FROM Person WHERE username = 'jane_user'), (SELECT id FROM Role WHERE authority = 'ROLE_USER'));

INSERT INTO person_role (person_id, role_id)
VALUES ((SELECT id FROM Person WHERE username = 'robert_user'), (SELECT id FROM Role WHERE authority = 'ROLE_USER'));

-- Alice Brown is a USER
INSERT INTO person_role (person_id, role_id)
VALUES ((SELECT id FROM Person WHERE username = 'alice_user'), (SELECT id FROM Role WHERE authority = 'ROLE_USER'));

-- Chris Wilson is a USER
INSERT INTO person_role (person_id, role_id)
VALUES ((SELECT id FROM Person WHERE username = 'chris_user'), (SELECT id FROM Role WHERE authority = 'ROLE_USER'));





INSERT INTO Book (book_name, author_name, publication_date, person_id, was_taken_at)
VALUES ('The Great Gatsby', 'F. Scott Fitzgerald', '1925-04-10', null, '2024-01-15'),
       ('To Kill a Mockingbird', 'Harper Lee', '1960-07-11', null, '2023-12-18'),
       ('1984', 'George Orwell', '1949-06-08', null, '2022-01-22'),
       ('Brave New World', 'Aldous Huxley', '1932-01-01', null, '2024-01-12'),
       ('The Catcher in the Rye', 'J.D. Salinger', '1951-07-16', null, '2022-01-28'),
       ('The Hobbit', 'J.R.R. Tolkien', '1937-09-21', 1, '2022-02-01'),
       ('The Lord of the Rings', 'J.R.R. Tolkien', '1954-07-29', 2, '2022-02-04'),
       ('Pride and Prejudice', 'Jane Austen', '1813-01-28', 3, '2022-02-08'),
       ('The Shining', 'Stephen King', '1977-01-28', 4, '2022-02-11'),
       ('Harry Potter and the Sorcerer Stone', 'J.K. Rowling', '1997-06-26', 5, '2022-02-15'),
       ('The Chronicles of Narnia', 'C.S. Lewis', '1950-10-16', 1, '2022-02-18'),
       ('The Da Vinci Code', 'Dan Brown', '2003-03-18', 2, '2022-02-22'),
       ('The Hitchhiker Guide to the Galaxy', 'Douglas Adams', '1979-10-12', 3, '2022-02-25'),
       ('The Girl with the Dragon Tattoo', 'Stieg Larsson', '2005-08-23', 4, '2022-02-28'),
       ('The Great Expectations', 'Charles Dickens', '1861-08-15', 5, '2022-03-04');
