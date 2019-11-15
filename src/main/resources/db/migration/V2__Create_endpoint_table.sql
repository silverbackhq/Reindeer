CREATE TABLE endpoint (
    id int(11) not null auto_increment,
    kind varchar(10) not null,
    method varchar(10) not null,
    uri varchar(60) not null,
    request_rules mediumtext not null,
    response_rules mediumtext not null,
    created_at datetime not null,
    updated_at datetime not null,
    primary key (id)
);