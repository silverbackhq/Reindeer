CREATE TABLE namespace (
    id int(11) not null auto_increment,
    name varchar(60) not null,
    slug varchar(60) not null,
    created_at datetime not null,
    updated_at datetime not null,
    PRIMARY KEY (id),
    UNIQUE KEY slug (slug)
);