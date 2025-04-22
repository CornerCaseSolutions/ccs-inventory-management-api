create table if not exists clothing (
       id uuid primary key default gen_random_uuid(),
       name varchar,
       description varchar,
       condition varchar,
       brand varchar,
       color varchar,
       type varchar,
       gender varchar,
       clothingSize varchar,
       status varchar,
       created timestamp,
       updated timestamp
);

create table if not exists users (
    id uuid primary key default gen_random_uuid(),
    email varchar,
    password varchar,
    role varchar,
    firstName varchar,
    lastName varchar,
    street varchar,
    city varchar,
    state varchar,
    zip varchar
);
