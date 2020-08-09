CREATE TABLE item (
    id int(11) not null auto_increment,
    name varchar(60) not null,
    slug varchar(60) not null,
    created_at datetime not null,
    updated_at datetime not null,
    primary key (id),
    unique key slug (slug)
);