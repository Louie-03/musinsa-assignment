drop table if exists product;
drop table if exists brand;
drop table if exists category;

create table brand
(
    id   bigint not null,
    name varchar(255),
    primary key (id)
);

create table category
(
    id   bigint not null,
    name varchar(255),
    primary key (id)
);

create table product
(
    id          bigint  not null,
    price       integer not null,
    brand_id    bigint,
    category_id bigint,
    foreign key (brand_id) references brand(id),
    foreign key (category_id) references category(id),
    primary key (id)
);
