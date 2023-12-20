drop table if exists  Book;
drop table if exists Person;
drop sequence if exists seq1;

create sequence seq1 as bigint start with 1;

create table Person(
    id bigint not null primary key default nextval('seq1'),
    name varchar(255) not null unique,
    birth_year integer not null
);

create table Book(
   id bigint not null primary key default nextval('seq1'),
   book_name varchar(255) not null unique,
   author_name varchar(255) not null,
   publication_year integer not null,
   person_id bigint references Person(id) on delete set null
);

INSERT INTO Person (name, birth_year) VALUES ('John Doe', 1985);
INSERT INTO Person (name, birth_year) VALUES ('Jane Smith', 1990);
INSERT INTO Person (name, birth_year) VALUES ('Michael Johnson', 1978);
INSERT INTO Person (name, birth_year) VALUES ('Emily Davis', 1995);
INSERT INTO Person (name, birth_year) VALUES ('Robert Wilson', 1980);
INSERT INTO Person (name, birth_year) VALUES ('Sophia Miller', 1992);
INSERT INTO Person (name, birth_year) VALUES ('Daniel White', 1987);
INSERT INTO Person (name, birth_year) VALUES ('Olivia Brown', 2000);
INSERT INTO Person (name, birth_year) VALUES ('William Taylor', 1975);
INSERT INTO Person (name, birth_year) VALUES ('Ella Anderson', 1998);

INSERT INTO Book (book_name, author_name, publication_year, person_id) VALUES ('Generation P', 'Viktor Pelevin', 1999, 1);
INSERT INTO Book (book_name, author_name, publication_year, person_id) VALUES ('The Master and Margarita', 'Mikhail Bulgakov', 1967, 2);
INSERT INTO Book (book_name, author_name, publication_year, person_id) VALUES ('Crime and Punishment', 'Fyodor Dostoyevsky', 1866, 3);
INSERT INTO Book (book_name, author_name, publication_year, person_id) VALUES ('The Brothers Karamazov', 'Fyodor Dostoyevsky', 1880, 4);
INSERT INTO Book (book_name, author_name, publication_year, person_id) VALUES ('Egil''s Saga', 'Snorri Sturluson', 1200, 5);
INSERT INTO Book (book_name, author_name, publication_year, person_id) VALUES ('The Prose Edda', 'Snorri Sturluson', 1200, 6);
INSERT INTO Book (book_name, author_name, publication_year, person_id) VALUES ('The Silmarillion', 'J.R.R. Tolkien', 1977, 7);
INSERT INTO Book (book_name, author_name, publication_year, person_id) VALUES ('The Two Towers', 'J.R.R. Tolkien', 1954, 8);
INSERT INTO Book (book_name, author_name, publication_year, person_id) VALUES ('Morphine', 'Mikhail Bulgakov', 1926, 9);
INSERT INTO Book (book_name, author_name, publication_year, person_id) VALUES ('The Hobbit', 'J.R.R. Tolkien', 1937, 10);