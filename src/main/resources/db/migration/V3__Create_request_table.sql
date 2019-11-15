CREATE TABLE request (
    id int(11) not null auto_increment,
    request mediumtext not null,
    response mediumtext not null,
    created_at datetime not null,
    updated_at datetime not null,
    primary key (id)
);