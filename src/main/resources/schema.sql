drop table if exists person_role;
drop table if exists Role;
drop table if exists Book;
drop table if exists Person;
drop sequence if exists seq1;

create sequence seq1 as bigint start with 1;

create table Person
(
    id            bigint       not null primary key default nextval('seq1'),
    name          varchar(255) not null,
    date_of_birth date         not null,
    books_limit int not null default 3,
    created_at    timestamp,
    username varchar(255) not null unique ,
    password varchar(255) not null
);

create table Book
(
    id               bigint       not null primary key default nextval('seq1'),
    book_name        varchar(255) not null unique,
    author_name      varchar(255) not null,
    publication_date date         not null,
    person_id        bigint       references Person (id) on delete set null,
    was_taken_at     date
);


create table Role
(
    id               bigint       not null primary key default nextval('seq1'),
    authority        varchar(255) not null unique
);

create table person_role
(
    person_id    bigint references PERSON(id) on update cascade on delete cascade,
    role_id bigint references Role(id) on update cascade on delete cascade,
    CONSTRAINT bill_product_pkey PRIMARY KEY (person_id, role_id)
);