CREATE TABLE request (
    id int(11) not null auto_increment,
    endpoint_id int(11) not null,
    request mediumtext not null,
    response mediumtext not null,
    created_at datetime not null,
    updated_at datetime not null,
    foreign key (endpoint_id) references endpoint(id) on delete cascade,
    primary key (id)
);