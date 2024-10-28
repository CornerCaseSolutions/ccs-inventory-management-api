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
