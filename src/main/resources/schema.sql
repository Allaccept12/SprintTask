drop table if exists Author;
drop table if exists Book;
drop table if exists BookAuthors;

create table Author(
    author_id bigint not null auto_increment ,
    name varchar(255) not null,
    birth varchar(255) not null,
    primary key(author_id)
)character set utf8mb4;

create table Book (
        book_id bigint not null auto_increment,
        name varchar(255) not null,
        extinction bit not null,
        total_pages integer not null,
        publication_of_year varchar(255) not null,
        isbn varchar(255) not null,
        price varchar(255) ,
        currency integer,
        primary key (book_id)
)character set utf8mb4;

create table BookAuthors(
    book_id bigint not null,
    author_id bigint not null
)character set utf8mb4;

alter table BookAuthors
    add constraint foreign key (book_id)
    references Book (book_id);

