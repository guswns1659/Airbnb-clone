drop table if exists picture;
drop table if exists reservation;
drop table if exists accommodation;
drop table if exists account;

create table accommodation (
       accommodation_id bigint not null auto_increment,
        available_guest_count integer not null,
        current_price integer not null,
        description longtext,
        discount_price integer not null,
        hotel_rating double precision not null,
        latitude double precision,
        location longtext,
        longitude double precision,
        accommodation_name varchar(255),
        previous_price integer not null,
        street longtext,
        primary key (accommodation_id)
);

create table account (
       dtype varchar(31) not null,
        account_id bigint not null auto_increment,
        account_email varchar(255) unique,
        primary key (account_id)
);

create table picture (
       picture_id bigint not null auto_increment,
        picture_url longtext,
        accommodation_id bigint,
        primary key (picture_id)
);

create table reservation (
       reservation_id bigint not null auto_increment,
        client_count integer,
        end_date date,
        price integer,
        start_date date,
        accommodation_id bigint,
        account_id bigint,
        primary key (reservation_id)
);


