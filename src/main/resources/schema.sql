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

insert into Person(name, birth_year) VALUES ('Vadim', 2001);
insert into Person(name, birth_year) VALUES ('Keknot', 1983);

insert into Book(book_name, author_name, publication_year, person_id) values ('Some book name', 'Pelevin', 2000, 1);
insert into Book(book_name, author_name, publication_year) values ('Basic fiction', 'Gradishevsky', 1960);